package com.sellsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.dao.RecordDao;
import com.sellsystem.dao.TaskDao;
import com.sellsystem.entity.Record;
import com.sellsystem.entity.Task;
import com.sellsystem.entity.enumerate.TaskEvent;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.service.TaskService;
import com.sellsystem.shiro.ShiroUtils;
import com.sellsystem.util.Constant;
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

    public MsgModel<PageInfo<Task>> getList(TaskSearchModel taskSearchModel) {
        String orderBy = Sortable.getOrderByString(taskSearchModel.getOrderBy());
        PageHelper.startPage(taskSearchModel.getPageNumber(), taskSearchModel.getPageSize(), orderBy);
        List<Task> taskList = taskDao.getList(taskSearchModel);
        PageInfo<Task> taskPageInfo = new PageInfo<>(taskList);
        return new MsgModel<>(taskPageInfo);
    }

    /**
     * 详情
     * @param taskId
     * @return
     */
    public MsgModel<Task> getTask(String taskId) {
        return new MsgModel<>(taskDao.getTask(taskId));
    }

    /**
     * 新增
     * @param task
     * @return
     */
    @Override
    public MsgModel<String> create(Task task) {
        MsgModel msgModel = new MsgModel();
        try {
            task.setState(engine.getStateByEngine(Constant.engineFile, task.getState(), TaskEvent.create.getEvent()));
            task.setCreateTime(new Date());
            taskDao.create(task);
            Record record = this.createRecord(task, TaskEvent.create.getValue());
            recordDao.create(record);
            msgModel.setData(task.getId());
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setData("新建失败");
        }
        return msgModel;
    }

    /**
     * 修改任务
     * @param task
     * @return
     */
    public MsgModel update(Task task) {
        MsgModel msgModel = new MsgModel();
        try {
            Task workTask = taskDao.getTask(task.getId());
            Record record = this.createRecord(workTask, Constant.updateTask);
            recordDao.create(record);
            //创建人不能修改
            task.setCreateUser(workTask.getCreateUser());
            taskDao.update(task);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setData("修改失败");
        }
        return msgModel;
    }

    /**
     * 删除任务
     * @param taskIdList
     * @return
     */
    public MsgModel delete(List<String> taskIdList) {
        MsgModel msgModel = new MsgModel();
        try {
            taskDao.delete(taskIdList);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setMessage("删除失败");
        }
        return msgModel;
    }

    /**
     * 生成record
     * @param task
     * @param action
     * @return
     */
    private Record createRecord(Task task, String action) {
        Record record = new Record();
        record.setExecutor(ShiroUtils.getUser());
        record.setAction(action);
        record.setTask(task);
        return record;
    }

}
