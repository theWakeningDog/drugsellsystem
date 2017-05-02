package com.sellsystem.dao;

import com.sellsystem.entity.Record;
import com.sellsystem.entity.Task;
import com.sellsystem.entity.searchmodel.extend.RecordSearchModel;
import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.util.MsgModel;
import org.apache.ibatis.annotations.Param;

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
    List<Task> getList(@Param("taskSearchModel") TaskSearchModel taskSearchModel);

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
    int create(@Param("task") Task task);

    /**
     * 修改
     * @param task
     * @return
     */
    int update(@Param("task") Task task);

    /**
     * 删除
     * @param taskIdList
     * @return
     */
    int delete(@Param("taskIdList") List<String> taskIdList);

}
