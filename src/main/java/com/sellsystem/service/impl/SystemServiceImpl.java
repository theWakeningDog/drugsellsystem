package com.sellsystem.service.impl;

import com.sellsystem.constant.ClassConstants;
import com.sellsystem.dao.SystemDao;
import com.sellsystem.entity.Setting;
import com.sellsystem.service.SystemService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwei on 2017/5/27.
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemDao systemDao;

    @Override
    public Setting get() {
        Setting setting = systemDao.get();
        return systemDao.get();
    }

    public MsgModel<String> create(Setting setting) {
        MsgModel msgModel = new MsgModel();
        try {
            systemDao.create(setting);
            msgModel.setData(setting.getId());
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("新增失败");
        }
        return msgModel;
    }

    public MsgModel<String> update(ArrayList<String> data) {
        MsgModel msgModel = new MsgModel();
        try {
            Setting setting = new Setting();
            setting.setKeys("drugUnit");
            setting.setValArr(data);
            systemDao.update(setting);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("修改失败");
        }
        return msgModel;
    }
}
