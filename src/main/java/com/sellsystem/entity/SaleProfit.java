package com.sellsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 销售利润
 * Created by zhangwei on 2017/5/24.
 */
public class SaleProfit {
    private String id;
    private Double profit;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SaleProfit{" +
                "id='" + id + '\'' +
                ", profit=" + profit +
                ", createTime=" + createTime +
                '}';
    }
}
