package com.yl.trend.service.impl;

import com.yl.trend.entity.User;
import com.yl.trend.mapper.UserMapper;
import com.yl.trend.service.UserService;
import com.yl.trend.utils.Const;
import com.yl.trend.utils.PageBean;
import com.yl.trend.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务逻辑
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;



   @Override
    public User findByOpenId(String openid) {
        if (!StringUtils.isEmpty(openid)){

            User user = userMapper.selectByOppenId(openid);

            return  user;
        }
        return null;
    }



    /**
     * 有选择性添加用户信息
     * @param user
     * @return
     */

    @Override
    public Boolean addUserInfos(User user) {
        if (user!=null){
            int mark = userMapper.insertSelective(user);
            if (mark>0){
                log.info("添加用户成功");
                return true;
            }
            }
        log.info("添加用户失败");
        return false;
    }

    @Override
    public PageBean<User> getUserList(User user, Integer page) {
        int allRow = getUserCount(user).intValue(); //总记录数
        int totalPage = PageUtil.countTotalPage(allRow, Const.PAGE_SIZE); //总页数
        int currentPage = PageUtil.countCurrentPage(page);//当前第几页
        int start = PageUtil.countStart(Const.PAGE_SIZE, currentPage); //起始记录数
        if(page>=0) {
            user.setStart(start);//起始记录
            user.setLength(Const.PAGE_SIZE);//每页显示的记录数
        }else {
            user.setStart(-1);//起始记录
            user.setLength(-1);//每页显示的记录数
        }

        List<User> users = userMapper.getUserList(user); //集合

        PageBean<User> pageBean = new PageBean<User>();
        pageBean.setAllRow(allRow);
        pageBean.setCurrentPage(currentPage);
        pageBean.setList(users);
        pageBean.setPageSize(Const.PAGE_SIZE);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public Long getUserCount(User user) {
        return userMapper.getUserCount(user);
    }

    /**
     * 获取用户的消费记录数据
     * @param openId
     * @return
     */
    @Override
    public List<User> getUserLoggers(String openId) {
        if (!"".equals(openId)){
            List<User> loggers = userMapper.getUserLogger(openId);
            return loggers;
        }
        log.info("你查询的数据不存在,请注意user的编号是否真确");
        return null;
    }

    /**
     * 先用户钱包里面添加钱
     * @param user
     * @return
     */
    @Override
    public Boolean updateUserSelectTive(User user) {

        int i=userMapper.updateByPrimaryKeySelective(user);
        if (i>0){
            log.info("添加金钱成功");
            return true;
        }
        log.info("添加失败");
        return false;
    }
}
