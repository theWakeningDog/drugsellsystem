package com.sellsystem.dao;

import com.sellsystem.entity.Drug;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
public interface DrugDao {

    /**
     * 别表
     * @return
     */
    List<Drug> getList();

    /**
     * 详情
     * @param drugId
     * @return
     */
    Drug getDrug(String drugId);
}
