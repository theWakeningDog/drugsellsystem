package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;
import org.springframework.util.StringUtils;

/**
 * Created by zhangwei on 2017/4/20.
 */
public class CustomerSearchModel extends PageSearchModel {
    private String name;
    private String type;
    private String createTime;

    public CustomerSearchModel() {
    }

    public CustomerSearchModel init() {
        this.setPageSize(0);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 按时间搜索时，分解时间获取开始时间，不用写属性，这样mybatis可以获取
     * @return
     */
    public String getCustomerTimeStart() {
        if (!StringUtils.isEmpty(createTime)) {
            String[] period = createTime.split(" - ");
            return period[0].trim();
        }
        return null;
    }

    /**
     * 按时间搜索时，分解时间获取结束时间
     * @return
     */
    public String getCustomerTimeEnd() {
        if (!StringUtils.isEmpty(createTime)) {
            String[] period = createTime.split(" - ");
            return period[1].trim();
        }
        return null;
    }
}
