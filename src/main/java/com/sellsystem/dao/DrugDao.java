package com.sellsystem.dao;

import com.sellsystem.entity.Drug;
import com.sellsystem.entity.DrugTask;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
public interface DrugDao {

    /**
     * 列表
     * @return
     */
    List<Drug> getList(@Param("drugSearchModel") DrugSearchModel drugSearchModel);

    /**
     * 通过任务id获得采购单或销售单列表
     * @param taskId
     * @return
     */
    List<Drug> getDrugByTask(@Param("taskId") String taskId);

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

    /**
     * 获取数量少于30的药品
     * @return
     */
    List<Drug> getDrugLessThan30();

    /**
     * 获得每种类别的总数
     * @return
     */
    List<Map<?, ?>> getSumBySort();

    /**
     * 创建任务和药品关联
     * @param dt
     * @return
     */
    int createDrugTask(@Param("dt") DrugTask dt);
}
