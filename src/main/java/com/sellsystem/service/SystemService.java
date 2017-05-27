package com.sellsystem.service;

import com.sellsystem.entity.Setting;
import com.sellsystem.util.MsgModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwei on 2017/5/27.
 */
public interface SystemService {

    Setting get();

    MsgModel<String> update(ArrayList<String> data);


}
