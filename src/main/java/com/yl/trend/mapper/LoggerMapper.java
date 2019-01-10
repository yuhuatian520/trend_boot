package com.yl.trend.mapper;

import com.yl.trend.entity.Logger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Created by Mybatis Generator 2018/12/26
*/
@Mapper
public interface LoggerMapper {
    int deleteByPrimaryKey(Integer transactionId);

    int insert(Logger record);

    int insertSelective(Logger record);

    Logger selectByPrimaryKey(Integer transactionId);

    int updateByPrimaryKeySelective(Logger record);

    int updateByPrimaryKey(Logger record);


    List<Logger> getUserLoggers(String openId);
}