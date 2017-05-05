package com.sellsystem.service.impl;

import com.sellsystem.dao.RecordDao;
import com.sellsystem.entity.Record;
import com.sellsystem.entity.searchmodel.extend.RecordSearchModel;
import com.sellsystem.service.RecordService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/2.
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDao recordDao;

    @Override
    public List<Record> getList(RecordSearchModel recordSearchModel) {
        return recordDao.getList(recordSearchModel);
    }
}
