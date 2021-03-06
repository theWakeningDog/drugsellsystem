package com.sellsystem.service;

import com.sellsystem.entity.Record;
import com.sellsystem.entity.searchmodel.extend.RecordSearchModel;
import com.sellsystem.util.MsgModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/2.
 */
public interface RecordService {

    /**
     * 日志列表
     * @param recordSearchModel
     * @return
     */
    List<Record> getList(RecordSearchModel recordSearchModel);
}
