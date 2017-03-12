package com.sellsystem.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.dao.ProcureOrderDao;
import com.sellsystem.entity.ProcureOrder;
import com.sellsystem.entity.searchmodel.extend.ProcureOrderSearchModel;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/12.
 */
@Service
public class ProcureOrderService {

    @Autowired
    private ProcureOrderDao procureOrderDao;

    public MsgModel<PageInfo<ProcureOrder>> getList(ProcureOrderSearchModel procureOrderSearchModel) {
        String orderBy = Sortable.getOrderByString(procureOrderSearchModel.getOrderBy());
        PageHelper.startPage(procureOrderSearchModel.getPageNumber(), procureOrderSearchModel.getPageSize(), orderBy);
        List<ProcureOrder> procureOrderList = procureOrderDao.getList(procureOrderSearchModel);
        PageInfo<ProcureOrder> procureOrderPageInfo = new PageInfo<>(procureOrderList);
        return new MsgModel<>(procureOrderPageInfo);
    }
}
