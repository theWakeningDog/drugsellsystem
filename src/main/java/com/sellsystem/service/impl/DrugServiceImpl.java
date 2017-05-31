package com.sellsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.constant.ClassConstants;
import com.sellsystem.dao.DrugDao;
import com.sellsystem.dao.DrugRecordDao;
import com.sellsystem.entity.Drug;
import com.sellsystem.entity.DrugRecord;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.service.DrugService;
import com.sellsystem.util.DateUtil;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
@Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugDao drugDao;
    @Autowired
    private DrugRecordDao drugRecordDao;

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

    /**
     * 退药
     * @param drugId
     * @param type
     * @param drugNum
     * @return
     */
    @Override
    public MsgModel outDrug(String drugId, String type, int drugNum, String remark) {
        MsgModel msgModel = new MsgModel();
        try {
            Drug drug = drugDao.getDrug(drugId);
            if (ClassConstants.DRUG_OUT_PURCHASE.equals(type)) {
                drug.setNumber(drug.getNumber() - drugNum);

                DrugRecord drugRecord = new DrugRecord();
                drugRecord.setDrug(drug);
                drugRecord.setNumber(drugNum);
                drugRecord.setAction("退还");
                drugRecord.setType("采购");
                drugRecord.setRemark(remark);
                drugRecord.setCreateTime(Calendar.getInstance().getTime());
                drugRecordDao.create(drugRecord);
            } else if (ClassConstants.DRUG_OUT_SALE.equals(type)) {
                drug.setNumber(drug.getNumber() + drugNum);

                DrugRecord drugRecord = new DrugRecord();
                drugRecord.setDrug(drug);
                drugRecord.setNumber(drugNum);
                drugRecord.setAction("退还");
                drugRecord.setType("销售");
                drugRecord.setRemark(remark);
                drugRecord.setCreateTime(Calendar.getInstance().getTime());
                drugRecordDao.create(drugRecord);
            }
            drugDao.update(drug);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.OPT_FAIL);
        }
        return msgModel;
    }

}
