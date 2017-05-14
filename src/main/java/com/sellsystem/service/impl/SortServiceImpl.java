package com.sellsystem.service.impl;

import com.sellsystem.dao.SortDao;
import com.sellsystem.entity.Sort;
import com.sellsystem.service.SortService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/14.
 */
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
}
