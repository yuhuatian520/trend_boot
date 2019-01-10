package com.yl.trend.mapper;

import com.yl.trend.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2018/11/30
*/
@Mapper
public interface AdminsMapper {


    int deleteByPrimaryKey(Integer aid);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin findAnameAndPassword(Admin admin);

    List<Admin> findAllAdmins();

    List<Admin> queryByAdminNameBlurry(@Param("aname") String aname);

}