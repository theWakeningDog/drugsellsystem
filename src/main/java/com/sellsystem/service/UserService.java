package com.sellsystem.service;

import com.github.pagehelper.PageInfo;
import com.sellsystem.entity.User;
import com.sellsystem.entity.searchmodel.extend.UserSearchModel;
import com.sellsystem.util.MsgModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/4/20.
 */
public interface UserService {

    /**
     * 列表
     * @param userSearchModel
     * @return
     */
    MsgModel<PageInfo<User>> getList(UserSearchModel userSearchModel);

    /**
     * 详情
     * @param userId
     * @return
     */
    MsgModel<User> getUser(String userId);

    /**
     * 新增
     * @param user
     * @return
     */
    MsgModel<String> create(User user);

    /**
     * 修改
     * @param user
     * @return
     */
    MsgModel update(User user);

    /**
     * 删除
     * @param userIdList
     * @return
     */
    MsgModel delete(List<String> userIdList);
}
