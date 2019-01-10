package com.yl.trend.mapper;

import com.yl.trend.entity.Logger;
import com.yl.trend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Mybatis Generator 2018/12/07
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByOppenId(String openid);

    List<User> getUserList(User user);

    Long getUserCount(User user);

    List<User> getUserLogger(String openId);

    List<Logger> getLogger(String openId);

}