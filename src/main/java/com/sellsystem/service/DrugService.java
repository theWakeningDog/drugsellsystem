package com.sellsystem.service;

import com.github.pagehelper.PageInfo;
import com.sellsystem.entity.Drug;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.util.MsgModel;

/**
 * Created by zhangwei on 2017/3/18.
 */
public interface DrugService {

    /**
     * 药品列表
     * @param drugSearchModel
     * @return
     */
    MsgModel<PageInfo<Drug>> getList(DrugSearchModel drugSearchModel);

    /**
     * 详情
     * @param drugId
     * @return
     */
    MsgModel<Drug> getDrug(String drugId);

    /**
     * 新增
     * @param drug
     * @return
     */
    MsgModel<String> create(Drug drug);

    /**
     * 修改
     * @param drug
     * @return
     */
    MsgModel update(Drug drug);
}

