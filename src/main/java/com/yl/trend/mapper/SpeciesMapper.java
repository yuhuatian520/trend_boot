package com.yl.trend.mapper;

import com.yl.trend.entity.Species;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Created by Mybatis Generator 2018/11/23
*/
@Mapper
public interface SpeciesMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(Species record);

    int insertSelective(Species record);

    Species selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(Species record);

    int updateByPrimaryKey(Species record);

    List<Species> findAll();


}