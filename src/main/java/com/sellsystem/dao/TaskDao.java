package com.sellsystem.dao;

import com.sellsystem.entity.Task;
import com.sellsystem.entity.searchmodel.TaskSearchModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/18.
 */
public interface TaskDao {

    /**
     * 列表
     * @param taskSearchModel
     * @return
     */
    List<Task> getList(TaskSearchModel taskSearchModel);

    /**
     * 详情
     * @param taskId
     * @return
     */
    Task getTask(String taskId);

    /**
     * 新建
     * @param task
     * @return
     */
    int create(Task task);

    /**
     * 修改
     * @param task
     * @return
     */
    int update(Task task);

    /**
     * 删除
     * @param taskIdList
     * @return
     */
    int delete(List<String> taskIdList);
}