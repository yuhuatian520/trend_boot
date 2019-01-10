package com.yl.trend.mapper;

import com.yl.trend.entity.Prize;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrizeMapper {
    int deleteByPrimaryKey(Integer prizeId);

    int insert(Prize record);

    int insertSelective(Prize record);

    Prize selectByPrimaryKey(Integer prizeId);

    int updateByPrimaryKeySelective(Prize record);

    int updateByPrimaryKey(Prize record);
}