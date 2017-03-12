package com.sellsystem.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.dao.DrugDao;
import com.sellsystem.entity.Drug;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
@Service
public class DrugService {

    @Autowired
    private DrugDao drugDao;

    /**
     * 药品列表
     * @param drugSearchModel
     * @return
     */
    public MsgModel<PageInfo<Drug>> getList(DrugSearchModel drugSearchModel) {
        LinkedHashMap<String, Boolean> orderBy = drugSearchModel.getOrderBy();
        PageHelper.startPage(drugSearchModel.getPageNumber(), drugSearchModel.getPageSize(), Sortable.getOrderByString(orderBy));
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
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setMessage("新增失败");
        }
        return msgModel;
    }

    public MsgModel update(Drug drug) {
        MsgModel msgModel = new MsgModel();
        try {
            drugDao.update(drug);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setMessage("修改失败");
        }
        return msgModel;
    }
}
