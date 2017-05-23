package com.sellsystem.service;

import com.sellsystem.entity.Sort;
import com.sellsystem.util.MsgModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwei on 2017/5/14.
 */
public interface SortService {

    /**
     * 获取类别列表
     * @return
     */
    MsgModel<List<Sort>> listSort();

    MsgModel createS(ArrayList<String> data);
}
