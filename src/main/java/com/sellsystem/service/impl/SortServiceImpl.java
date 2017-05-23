package com.sellsystem.service.impl;

import com.sellsystem.constant.ClassConstants;
import com.sellsystem.dao.SortDao;
import com.sellsystem.entity.Sort;
import com.sellsystem.service.SortService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangwei on 2017/5/14.
 */
@Service
public class SortServiceImpl implements SortService {

    @Autowired
    private SortDao sortDao;

    /**
     * 类别列表
     * @return
     */
    @Override
    public MsgModel<List<Sort>> listSort() {
        return new MsgModel<>(sortDao.getList());
    }

    @Override
    public MsgModel createS(ArrayList<String> data) {
        MsgModel msgModel = new MsgModel();
        try {
            List<Sort> wList = sortDao.getList();
            for (Sort w : wList) {
                w.setDel(ClassConstants.successDelete);
                sortDao.update(w);
            }
            for (int i = 0; i < data.size(); i++) {
                Sort w = new Sort();
                w.setName(data.get(i));
                w.setOrders(i);
                w.setCreateTime(new Date());
                sortDao.create(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.SORT_OPT_FAIL);
        }
        return msgModel;
    }
}
