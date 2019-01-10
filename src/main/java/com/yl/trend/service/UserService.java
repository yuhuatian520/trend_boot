package com.yl.trend.service;

import com.yl.trend.entity.User;
import com.yl.trend.utils.PageBean;

import java.util.List;

public interface UserService {

    public User findByOpenId(String openid);

    public Boolean addUserInfos(User user);



    /**
     * 根据分页条件查询用户信息
     * @param user 查询条件
     * @param page 页数
     * @return
     */
    public PageBean<User> getUserList(User user, Integer page);

    /**
     * 返回总记录数
     * @param user
     * @return
     */
    public Long getUserCount(User user);

    List<User> getUserLoggers(String openId);


    Boolean updateUserSelectTive(User user);

}
