package com.sellsystem.entity;

/**
 * 药品和任务关联
 * Created by zhangwei on 2017/5/29.
 */
public class DrugTask {
    private Drug drug;
    private Task task;
    private String taskType;//购买还是销售
    private Integer drugNum;//处理数量

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getDrugNum() {
        return drugNum;
    }

    public void setDrugNum(Integer drugNum) {
        this.drugNum = drugNum;
    }
}
