package com.sellsystem.dao;

import com.sellsystem.entity.Setting;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangwei on 2017/5/27.
 */
public interface SystemDao {

    /**
     * 获得药品单位
     * @return
     */
    Setting get();

    /**
     * 新增
     * @param setting
     * @return
     */
    int create(@Param("setting") Setting setting);

    /**
     * 修改
     * @param setting
     * @return
     */
    int update(@Param("setting") Setting setting);
}
