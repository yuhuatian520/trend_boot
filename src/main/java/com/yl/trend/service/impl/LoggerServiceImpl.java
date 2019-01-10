package com.yl.trend.service.impl;

import com.yl.trend.entity.Logger;
import com.yl.trend.mapper.LoggerMapper;
import com.yl.trend.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LoggerServiceImpl implements LoggerService {

    @Autowired
   private LoggerMapper loggerMapper;

    /**
     * 添加记录的信息
     * @param logger
     * @return
     */
    @Override
    public Integer addLoggerInfo(Logger logger) {
        if (logger!=null){
            int i = loggerMapper.insertSelective(logger);
            if (i>0){
                log.info("添加成功");
            }else{
                log.info("添加失败");
            }
            return i;
        }

        return null;
    }

    @Override
    public List<Logger> getUserLoggers(String openId) {

        if (!"".equals(openId)){
            return loggerMapper.getUserLoggers(openId);
        }
        return null;
    }
}
