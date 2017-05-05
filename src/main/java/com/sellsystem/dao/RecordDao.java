package com.sellsystem.dao;

import com.sellsystem.entity.Record;
import com.sellsystem.entity.searchmodel.extend.RecordSearchModel;
import com.sellsystem.util.MsgModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/2.
 */
public interface RecordDao {

    /**
     * 日志列表
     * @param recordSearchModel
     * @return
     */
    List<Record> getList(@Param("recordSearchModel") RecordSearchModel recordSearchModel);

    int create(@Param("record") Record record);
}
