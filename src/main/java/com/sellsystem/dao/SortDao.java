package com.sellsystem.dao;

import com.sellsystem.entity.Sort;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/13.
 */
public interface SortDao {
    /**
     * 类别列表
     * @return
     */
    List<Sort> getList();

    /**
     * 新建
     * @param sort
     */
    void create(@Param("sort") Sort sort);

    /**
     * 修改
     * @param sort
     * @return
     */
    int update(@Param("sort") Sort sort);
}
