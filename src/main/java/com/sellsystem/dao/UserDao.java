package com.sellsystem.dao;

import com.sellsystem.entity.User;
import com.sellsystem.entity.searchmodel.extend.UserSearchModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/19.
 */
public interface UserDao {

    /**
     * 列表
     * @param userSearchModel
     * @return
     */
    List<User> getList(UserSearchModel userSearchModel);

    /**
     * 详情
     * @param userId
     * @return
     */
    User getUser(String userId);

    /**
     * 新增
     * @param user
     * @return
     */
    int create(User user);

    /**
     * 修改
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 删除
     * @param userIdList
     * @return
     */
    int delete(List<String> userIdList);
}
