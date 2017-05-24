package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;
import org.springframework.util.StringUtils;

/**
 * Created by zhangwei on 2017/4/20.
 */
public class SaleProfitSearchModel extends PageSearchModel {
    private String createTime;

    public SaleProfitSearchModel() {
    }

    public SaleProfitSearchModel init() {
        this.setPageSize(0);
        return this;
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
    public String getCreateTimeStart() {
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
    public String getCreateTimeEnd() {
        if (!StringUtils.isEmpty(createTime)) {
            String[] period = createTime.split(" - ");
            return period[1].trim();
        }
        return null;
    }
}
