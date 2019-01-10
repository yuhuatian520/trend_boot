package com.yl.trend.service;

import com.yl.trend.entity.Logger;

import java.util.List;

public interface LoggerService {

    Integer addLoggerInfo(Logger logger);

    List<Logger> getUserLoggers(String openId);



}
