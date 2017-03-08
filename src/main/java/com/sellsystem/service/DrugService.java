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

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zhangwei on 2017/3/4/004.
 */
@Service
public class DrugService {

    @Autowired
    private DrugDao drugDao;

    public MsgModel<PageInfo<Drug>> getList(DrugSearchModel drugSearchModel) {
        LinkedHashMap<String, Boolean> orderBy = drugSearchModel.getOrderBy();
        PageHelper.startPage(drugSearchModel.getPageNumber(), drugSearchModel.getPageSize(), Sortable.getOrderByString(orderBy));
        List<Drug> drugList = drugDao.getList();
        PageInfo<Drug> drugPageInfo = new PageInfo<>(drugList);
        return  new MsgModel<>("", drugPageInfo);
    }
}
