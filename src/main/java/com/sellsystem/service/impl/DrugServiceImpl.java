package com.sellsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.constant.ClassConstants;
import com.sellsystem.dao.DrugDao;
import com.sellsystem.entity.Drug;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.service.DrugService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
@Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugDao drugDao;

    /**
     * 药品列表
     * @param drugSearchModel
     * @return
     */
    public MsgModel<PageInfo<Drug>> getList(DrugSearchModel drugSearchModel) {
//        LinkedHashMap<String, Boolean> orderBy = drugSearchModel.getOrderBy();
//        PageHelper.startPage(drugSearchModel.getPageNumber(), drugSearchModel.getPageSize(), Sortable.getOrderByString(orderBy));
        List<Drug> drugList = drugDao.getList(drugSearchModel);
        PageInfo<Drug> drugPageInfo = new PageInfo<>(drugList);
        return  new MsgModel<>("", drugPageInfo);
    }

    /**
     * 详情
     * @param drugId
     * @return
     */
    public MsgModel<Drug> getDrug(String drugId) {
        return new MsgModel<>("", drugDao.getDrug(drugId));
    }

    /**
     * 新增
     * @param drug
     * @return
     */
    public MsgModel<String> create(Drug drug) {
        MsgModel msgModel = new MsgModel();
        try {
            drug.setCreateTime(new Date());
            drugDao.create(drug);
            msgModel.setData(drug.getId());
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("新增失败");
        }
        return msgModel;
    }

    /**
     * 修改
     * @param drug
     * @return
     */
    public MsgModel update(Drug drug) {
        MsgModel msgModel = new MsgModel();
        try {
            drugDao.update(drug);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("修改失败");
        }
        return msgModel;
    }

    /**
     * 获取数量少于30的药品
     * @return
     */
    @Override
    public List<Drug> getDrugLessThan30() {
        return drugDao.getDrugLessThan30();
    }

    @Override
    public List<Map<?, ?>> getSumBySort() {
        return drugDao.getSumBySort();
    }

}
