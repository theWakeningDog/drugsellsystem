package com.sellsystem.entity.searchmodel;

import java.util.LinkedHashMap;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
public class PageSearchModel implements Sortable {
    private int pageSize = 10;
    private int pageNumber = 1;
    private LinkedHashMap<String, Boolean> orderBy = new LinkedHashMap();
    private String createTime;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public LinkedHashMap<String, Boolean> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(LinkedHashMap<String, Boolean> orderBy) {
        this.orderBy = orderBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
