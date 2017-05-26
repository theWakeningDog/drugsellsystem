package com.sellsystem.dao;

import com.sellsystem.entity.User;
import com.sellsystem.entity.searchmodel.extend.UserSearchModel;
import org.apache.ibatis.annotations.Param;

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
    List<User> getList(@Param("userSearchModel") UserSearchModel userSearchModel);

    /**
     * 详情
     * @param userId
     * @return
     */
    User getUser(@Param("userId") String userId);

    /**
     * 通过账号查找
     * @param account
     * @return
     */
    User getUserByAccount(@Param("account") String account);

    /**
     * 新增
     * @param user
     * @return
     */
    int create(@Param("user") User user);

    /**
     * 修改
     * @param user
     * @return
     */
    int update(@Param("user") User user);

    /**
     * 删除
     * @param userIdList
     * @return
     */
    int delete(@Param("userIdList") List<String> userIdList);

    /**
     * 获得创建时间最大的人
     * @return
     */
    User getMaxTimeUser();
}
