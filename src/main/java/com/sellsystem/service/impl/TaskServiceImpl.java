package com.sellsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.dao.*;
import com.sellsystem.entity.*;
import com.sellsystem.entity.enumerate.TaskEventEnum;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.SaleProfitSearchModel;
import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.service.TaskService;
import com.sellsystem.shiro.ShiroUtils;
import com.sellsystem.constant.ClassConstants;
import com.sellsystem.util.DateUtil;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangwei on 2017/3/18.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private StateMachineEngine engine;
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private DrugDao drugDao;
    @Autowired
    private SaleRecordDao saleRecordDao;
    @Autowired
    private SaleProfitDao saleProfitDao;
    @Autowired
    private DrugRecordDao drugRecordDao;

    public MsgModel<PageInfo<Task>> getList(TaskSearchModel taskSearchModel) {
//        String orderBy = Sortable.getOrderByString(taskSearchModel.getOrderBy());
//        PageHelper.startPage(taskSearchModel.getPageNumber(), taskSearchModel.getPageSize(), orderBy);
        List<Task> taskList = taskDao.getList(taskSearchModel);
        PageInfo<Task> taskPageInfo = new PageInfo<>(taskList);
        return new MsgModel<>(taskPageInfo);
    }

    /**
     * 详情
     *
     * @param taskId
     * @return
     */
    public MsgModel<Task> getTask(String taskId) {
        return new MsgModel<>(taskDao.getTask(taskId));
    }

    /**
     * 新增
     *
     * @param task
     * @return
     */
    @Override
    public MsgModel<String> create(Task task) {
        MsgModel msgModel = new MsgModel();
        try {
            task.setCreateUser(ShiroUtils.getUser());
            task.setState(engine.getStateByEngine(ClassConstants.ENGINE_FILE, task.getState(), TaskEventEnum.create.getEvent()));
            task.setCreateTime(new Date());
            taskDao.create(task);
            Record record = this.createRecord(task, TaskEventEnum.create.getValue());
            recordDao.create(record);
            msgModel.setData(task.getId());
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setData(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 修改任务
     *
     * @param task
     * @return
     */
    public MsgModel update(Task task) {
        MsgModel msgModel = new MsgModel();
        try {
            Task workTask = taskDao.getTask(task.getId());
            Record record = this.createRecord(workTask, ClassConstants.UPDATE_TASK);
            recordDao.create(record);
            //创建人不能修改
            task.setCreateUser(workTask.getCreateUser());
            taskDao.update(task);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setData(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 删除任务
     *
     * @param taskIdList
     * @return
     */
    public MsgModel delete(List<String> taskIdList) {
        MsgModel msgModel = new MsgModel();
        try {
            //taskDao.delete(taskIdList);
            for (String taskId : taskIdList) {
                Task task = taskDao.getTask(taskId);
                task.setIsDelete(ClassConstants.successDelete);
                taskDao.update(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 指派
     *
     * @param task
     * @return
     */
    @Override
    public MsgModel allotTask(Task task) {
        MsgModel msgModel = new MsgModel();
        try {
            Task workTask = taskDao.getTask(task.getId());
            task.setState(engine.getStateByEngine(
                    ClassConstants.ENGINE_FILE, workTask.getState(), TaskEventEnum.allot.getEvent()));
            task.setCreateUser(workTask.getCreateUser());
            task.setExecutor(ShiroUtils.getUser());
            taskDao.update(task);

            Record record = this.createRecord(task, TaskEventEnum.allot.getValue());
            record.setExecutor(ShiroUtils.getUser());
            recordDao.create(record);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 取消
     *
     * @param task
     * @return
     */
    @Override
    public MsgModel offTask(Task task) {
        MsgModel msgModel = new MsgModel();
        try {
            Task workTask = taskDao.getTask(task.getId());
//            task.setState(engine.getStateByEngine(
//                    ClassConstants.ENGINE_FILE, workTask.getState(), TaskEventEnum.off.getValue()));
            task.setState("offed");
            taskDao.update(task);

            Record record = this.createRecord(task, TaskEventEnum.off.getValue());
            recordDao.create(record);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 采购完成,药品添加处理
     *
     * @param task
     * @return
     */
    @Override
    public MsgModel finishPurchaseTask(Task task, List<Drug> drugList) {
        MsgModel msgModel = new MsgModel();
        try {
            Task workTask = taskDao.getTask(task.getId());
            task.setState(engine.getStateByEngine(
                    ClassConstants.ENGINE_FILE, workTask.getState(), TaskEventEnum.finish.getEvent()));
            task.setCreateUser(workTask.getCreateUser());
            task.setCompleteTime(DateUtil.getCurrentDayDate());
            taskDao.update(task);

            Record record = this.createRecord(task, TaskEventEnum.finish.getValue());
            recordDao.create(record);

            if (drugList.size() > 0) {
                for (Drug drug : drugList) {

                    //生成药品
                    drug.setCreateTime(new Date());
                    drugDao.create(drug);

                    //药品记录
                    DrugRecord drugRecord = new DrugRecord();
                    drugRecord.setExecutor(ShiroUtils.getUser());
                    drugRecord.setDrug(drug);
                    drugRecord.setNumber(drug.getNumber());
                    drugRecord.setAction("采购");
                    drugRecord.setCreateTime(Calendar.getInstance().getTime());
                    drugRecordDao.create(drugRecord);

                    //创建药品与任务关联
                    DrugTask dt = new DrugTask();
                    dt.setDrug(drug);
                    dt.setTask(task);
                    dt.setTaskType(task.getType());
                    dt.setDrugNum(drug.getNumber());
                    drugDao.createDrugTask(dt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 销售任务
     * @param task
     * @param drugList
     * @return
     */
    @Override
    public MsgModel finishSaleTask(Task task, List<Drug> drugList) {
        MsgModel msgModel = new MsgModel();
        try {
            Task workTask = taskDao.getTask(task.getId());
            task.setState(engine.getStateByEngine(
                    ClassConstants.ENGINE_FILE, workTask.getState(), TaskEventEnum.finish.getEvent()));
            task.setCreateUser(workTask.getCreateUser());
            task.setCompleteTime(DateUtil.getCurrentDayDate());
            taskDao.update(task);

            Record record = this.createRecord(task, TaskEventEnum.finish.getValue());
            recordDao.create(record);

            if (drugList.size() > 0) {
                for (Drug d : drugList) {

                    //药品记录
                    DrugRecord drugRecord = new DrugRecord();
                    drugRecord.setExecutor(ShiroUtils.getUser());
                    drugRecord.setDrug(d);
                    drugRecord.setNumber(d.getNumber());
                    drugRecord.setAction("销售");
                    drugRecord.setCreateTime(Calendar.getInstance().getTime());
                    drugRecordDao.create(drugRecord);

                    //销售记录
                    SaleRecord saleRecord = new SaleRecord();
                    saleRecord.setDrug(d);
                    saleRecord.setSaleNumber(d.getNumber());
                    saleRecord.setSaleDate(new Date());
                    saleRecordDao.create(saleRecord);

                    //创建药品与任务关联,和上面重复，去掉上面。
                    DrugTask dt = new DrugTask();
                    dt.setDrug(d);
                    dt.setTask(task);
                    dt.setTaskType(task.getType());
                    dt.setDrugNum(d.getNumber());
                    drugDao.createDrugTask(dt);


                    Drug drugFormDB = drugDao.getDrug(d.getId());
                    //添加利润
                    boolean flag = true;
                    List<SaleProfit> saleProfitList = saleProfitDao.getList(new SaleProfitSearchModel());
                    if (null != saleProfitList && saleProfitList.size() > 0) {
                        for (SaleProfit saleProfit : saleProfitList) {
                            if (DateUtil.getCurrentDay().equals(DateUtil.dateToString(saleProfit.getCreateTime()))) {
                                saleProfit.setProfit(saleProfit.getProfit() + d.getNumber() * (drugFormDB.getRetail() - drugFormDB.getPurchase()));
                                saleProfit.setCreateTime(null);
                                saleProfitDao.update(saleProfit);
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        SaleProfit saleProfit = new SaleProfit();
                        saleProfit.setProfit(d.getNumber() * (drugFormDB.getRetail() - drugFormDB.getPurchase()));
                        saleProfit.setCreateTime(new Date());
                        saleProfitDao.create(saleProfit);
                    }


                    Drug drug = drugDao.getDrug(d.getId());
                    d.setNumber(drug.getNumber() - d.getNumber()); //修改一下数量
                    drugDao.update(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 关闭
     *
     * @param task
     * @return
     */
    @Override
    public MsgModel closeTask(Task task) {
        MsgModel msgModel = new MsgModel();
        try {
            Task workTask = taskDao.getTask(task.getId());
            task.setState(engine.getStateByEngine(
                    ClassConstants.ENGINE_FILE, workTask.getState(), TaskEventEnum.close.getEvent()));
            taskDao.update(task);
            Record record = this.createRecord(task, TaskEventEnum.close.getValue());
            recordDao.create(record);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 获得与任务相关的销售单或采购单列表
     * @param taskId
     * @return
     */
    @Override
    public List<Drug> getDrugByTask(String taskId) {
        return drugDao.getDrugByTask(taskId);
    }

    /**
     * 生成record
     *
     * @param task
     * @param action
     * @return
     */
    private Record createRecord(Task task, String action) {
        Record record = new Record();
        record.setCreateUser(ShiroUtils.getUser());
        record.setAction(action);
        record.setTask(task);
        record.setCreateTime(new Date());
        return record;
    }
}
