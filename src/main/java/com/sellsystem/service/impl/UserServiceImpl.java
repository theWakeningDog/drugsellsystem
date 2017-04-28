package com.sellsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.dao.UserDao;
import com.sellsystem.entity.User;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.UserSearchModel;
import com.sellsystem.service.UserService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangwei on 2017/4/20.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 列表
     * @param userSearchModel
     * @return
     */
    @Override
    public MsgModel<PageInfo<User>> getList(UserSearchModel userSearchModel) {
        String orderBy = Sortable.getOrderByString(userSearchModel.getOrderBy());
        PageHelper.startPage(userSearchModel.getPageNumber(), userSearchModel.getPageSize(), orderBy);
        List<User> userList = userDao.getList(userSearchModel);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        return new MsgModel<>(userPageInfo);
    }

    /**
     * 详情
     * @param userId
     * @return
     */
    @Override
    public MsgModel<User> getUser(String userId) {
        return new MsgModel<>(userDao.getUser(userId));
    }

    /**
     * 通过账号查找
     * @param account
     * @return
     */
    @Override
    public MsgModel<User> getUserByAccount(String account) {
        return new MsgModel<>(userDao.getUserByAccount(account));
    }

    /**
     * 新增
     * @param user
     * @return
     */
    @Override
    public MsgModel<String> create(User user) {
        MsgModel msgModel = new MsgModel();
        try {
            user.setCreateTime(new Date());
            userDao.create(user);
            msgModel.setData(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setData("新建失败");
        }
        return msgModel;
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @Override
    public MsgModel update(User user) {
        MsgModel msgModel = new MsgModel();
        try {
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setData("修改失败");
        }
        return msgModel;
    }

    /**
     * 删除
     * @param userIdList
     * @return
     */
    @Override
    public MsgModel delete(List<String> userIdList) {
        MsgModel msgModel = new MsgModel();
        try {
            userDao.delete(userIdList);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setMessage("删除失败");
        }
        return msgModel;
    }
}
