package com.sellsystem.entity.searchmodel;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
public class PageSearchModel implements Sortable {
    private int pageSize = 10;
    private int pageNumber = 1;
    //排序
    private LinkedHashMap<String, Boolean> orderBy = new LinkedHashMap();
    //高级搜索面板是否是关闭。注意，由于是is...  在set的时候自动把is去掉了，所以在html中也不能有is。即html中el表达式需要和set保持一致
    private boolean isSearchClosed = true;

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

    public boolean isSearchClosed() {
        return isSearchClosed;
    }

    public void setSearchClosed(boolean searchClosed) {
        isSearchClosed = searchClosed;
    }
}
