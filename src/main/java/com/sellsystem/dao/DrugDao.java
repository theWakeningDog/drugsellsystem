package com.sellsystem.dao;

import com.sellsystem.entity.Drug;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
public interface DrugDao {

    /**
     * 别表
     * @return
     */
    List<Drug> getList(@Param("drugSearchModel") DrugSearchModel drugSearchModel);

    /**
     * 详情
     * @param drugId
     * @return
     */
    Drug getDrug(@Param("drugId") String drugId);

    /**
     * 新增
     * @param drug
     * @return
     */
    int create(@Param("drug") Drug drug);

    /**
     * 修改
     * @param drug
     * @return
     */
    int update(@Param("drug") Drug drug);
}
