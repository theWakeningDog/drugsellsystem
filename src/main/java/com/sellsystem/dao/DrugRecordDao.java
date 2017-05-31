package com.sellsystem.dao;

import com.sellsystem.entity.DrugRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by zhangwei on 2017/5/31.
 */
public interface DrugRecordDao {

    /**
     * 创建
     * @param drugRecord
     * @return
     */
    int create(@Param("drugRecord") DrugRecord drugRecord);
}
