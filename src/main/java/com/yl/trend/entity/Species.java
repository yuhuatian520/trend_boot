package com.yl.trend.entity;

import lombok.Data;

import java.util.List;

/**
* Created by Mybatis Generator 2018/11/23
*/
@Data
public class Species {
    private Integer sid;

    private String sname;

    private List<Resume> resume;
}