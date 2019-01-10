package com.yl.trend.mapper;

import com.yl.trend.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by Mybatis Generator 2018/11/23
 */
@Mapper
public interface ResumeMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);

    /**
     * 根据分页条件查询简历信息
     *
     * @param resume 查询条件
     * @return 用户信息
     */
    List<Resume> getResumeList();

    //List<Resume> queryResumeLists();

    /**
     * 根据条件查询简历的数量
     *
     * @param resume 查询条件
     * @return 简历的数量
     */
    Long getResumeCount(Resume resume);

    /**
     * 获取模板的信息
     * @param uid
     * @return
     */
    Resume findOne(Integer rid);

    /**
     * 根据会员编码查询vip列表
     * @param vip
     * @return
     */
    Set<Resume> getResumeInfo();

    /**
     * 模糊随机查询
     * @param rname
     * @return
     */

    Set<Resume> getResumeNameBlurry(@Param("rname") String rname);

   int updateAll(Resume resume);


}