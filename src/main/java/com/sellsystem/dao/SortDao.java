package com.sellsystem.dao;

import com.sellsystem.entity.Sort;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/13.
 */
public interface SortDao {
    /**
     * 类别列表
     * @return
     */
    List<Sort> getList();
}
