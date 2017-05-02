package com.sellsystem.service;

import com.github.pagehelper.PageInfo;
import com.sellsystem.entity.Record;
import com.sellsystem.entity.Task;
import com.sellsystem.entity.searchmodel.extend.RecordSearchModel;
import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.util.MsgModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/18.
 */
public interface TaskService {

    /**
     * 列表
     * @param taskSearchModel
     * @return
     */
    MsgModel<PageInfo<Task>> getList(TaskSearchModel taskSearchModel);

    /**
     * 详情
     * @param taskId
     * @return
     */
    MsgModel<Task> getTask(String taskId);

    /**
     * 日志信息
     * @param recordSearchModel
     * @return
     */
    MsgModel<List<Record>> getRecordList(RecordSearchModel recordSearchModel);

    /**
     * 新增
     * @param task
     * @return
     */
    MsgModel<String> create(Task task);

    /**
     * 修改
     * @param task
     * @return
     */
    MsgModel update(Task task);

    /**
     * 删除
     * @param taskIdList
     * @return
     */
    MsgModel delete(List<String> taskIdList);
}
