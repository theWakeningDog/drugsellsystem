package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;

/**
 * Created by zhangwei on 2017/4/20.
 */
public class UserSearchModel extends PageSearchModel {

    private String keyword;

    public UserSearchModel init() {
        this.setPageSize(0);
        return this;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
