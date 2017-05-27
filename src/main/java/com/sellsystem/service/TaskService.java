package com.sellsystem.service;

import com.github.pagehelper.PageInfo;
import com.sellsystem.entity.Drug;
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

    /**
     * 指派
     * @param task
     * @return
     */
    MsgModel allotTask(Task task);

    /**
     * 取消
     * @param task
     * @return
     */
    MsgModel offTask(Task task);

    /**
     * 完成采购任务
     * @param task
     * @param drugList
     * @return
     */
    MsgModel finishPurchaseTask(Task task, List<Drug> drugList);

    /**
     * 销售任务
     * @param task
     * @param drugList
     * @return
     */
    MsgModel finishSaleTask(Task task, List<Drug> drugList);

    /**
     * 关闭
     * @param task
     * @return
     */
    MsgModel closeTask(Task task);

    /**
     * 获得与任务相关的销售单或采购单列表
     * @param taskId
     * @return
     */
    List<Drug> getDrugByTask(String taskId);
}
