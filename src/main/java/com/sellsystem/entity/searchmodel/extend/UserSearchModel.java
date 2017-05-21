package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;

/**
 * Created by zhangwei on 2017/4/20.
 */
public class UserSearchModel extends PageSearchModel {

    public UserSearchModel init() {
        this.setPageSize(0);
        return this;
    }
}
