package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;
import org.springframework.util.StringUtils;

/**
 * 药品搜索条件
 * Created by zhangwei on 2017/3/4/004.
 */
public class DrugSearchModel extends PageSearchModel {
    private String name;
    //仓库名
    private String warehouseId;
    //类别名
    private String sortId;
    //通用名，舍弃
    private String commonName;
    //有效期
    private String periodTime;
    //是否过期 0:全部 1：未过期2：已过期
    private Integer drugOver = 0;

    public DrugSearchModel init() {
        this.setPageSize(0);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    public Integer getDrugOver() {
        return drugOver;
    }

    public void setDrugOver(Integer drugOver) {
        this.drugOver = drugOver;
    }

    /**
     * 按时间搜索时，分解时间获取开始时间，不用写属性，这样mybatis可以获取
     * @return
     */
    public String getPeriodTimeStart() {
        if (!StringUtils.isEmpty(periodTime)) {
            String[] period = periodTime.split(" - ");
            return period[0].trim();
        }
        return null;
    }

    /**
     * 按时间搜索时，分解时间获取结束时间
     * @return
     */
    public String getPeriodTimeEnd() {
        if (!StringUtils.isEmpty(periodTime)) {
            String[] period = periodTime.split(" - ");
            return period[1].trim();
        }
        return null;
    }
}
