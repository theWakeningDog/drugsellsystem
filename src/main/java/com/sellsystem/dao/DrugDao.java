package com.sellsystem.dao;

import com.sellsystem.entity.Drug;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
public interface DrugDao {

    /**
     * 别表
     * @return
     */
    List<Drug> getList(DrugSearchModel drugSearchModel);

    /**
     * 详情
     * @param drugId
     * @return
     */
    Drug getDrug(String drugId);

    /**
     * 新增
     * @param drug
     * @return
     */
    int create(Drug drug);
}
