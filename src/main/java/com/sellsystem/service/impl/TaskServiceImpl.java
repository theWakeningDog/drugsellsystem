package com.sellsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.dao.DrugDao;
import com.sellsystem.dao.RecordDao;
import com.sellsystem.dao.TaskDao;
import com.sellsystem.entity.Drug;
import com.sellsystem.entity.Record;
import com.sellsystem.entity.Task;
import com.sellsystem.entity.User;
import com.sellsystem.entity.enumerate.TaskEventEnum;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.service.TaskService;
import com.sellsystem.shiro.ShiroUtils;
import com.sellsystem.constant.ClassConstants;
import com.sellsystem.util.DateUtil;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            //先把user定死
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
            task.setExecutor(this.getUser());
            taskDao.update(task);

            Record record = this.createRecord(task, TaskEventEnum.allot.getValue());
            record.setExecutor(this.getUser());
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
            task.setState(engine.getStateByEngine(
                    ClassConstants.ENGINE_FILE, workTask.getState(), TaskEventEnum.off.getValue()));
            taskDao.update(task);

            Record record = this.createRecord(task, TaskEventEnum.finish.getValue());
            recordDao.create(record);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.TASK_OPT_FAIL);
        }
        return msgModel;
    }

    /**
     * 完成,药品处理
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
                    drugDao.create(drug);
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
        record.setCreateTime(DateUtil.getCurrentDayDate());
        return record;
    }

    //假的，需要修改
    private User getUser() {
        User user = new User();
        user.setId("c8ab7c2d-0c87-11e7-8d59-0021cc62c2f3");
        return user;
    }
}
