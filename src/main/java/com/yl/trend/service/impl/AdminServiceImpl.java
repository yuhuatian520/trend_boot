package com.yl.trend.service.impl;


import com.yl.trend.entity.Admin;
import com.yl.trend.mapper.AdminsMapper;
import com.yl.trend.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminsMapper adminMapper;

    @Override
    public Admin findNameAndPassword(Admin admin) {
        if (admin!=null){

            Admin count = adminMapper.findAnameAndPassword(admin);
            return count;
        }

        return null;
    }

    @Override
    public Boolean addAdmins(Admin admin) {
        try {
            if (admin!=null){
                int i = adminMapper.insert(admin);
                if (i>0){
                    System.out.println("添加管理员成功");
                    return true;
                }

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Admin> findAll() {

        List<Admin> admins = adminMapper.findAllAdmins();
        return admins;
    }

    /**
     * 管理员模糊查询操作
     * @param
     * @return
     */
    @Override
    public List<Admin> queryAdminNameBlurry(String aname) {
          if (aname!=null&&aname!=""){
              List<Admin> admins = adminMapper.queryByAdminNameBlurry(aname);
              return admins;
          }
        return null;
    }

    @Override
    public Boolean deleteAdmin(Integer aid) {

        try {
            if (aid!=0){
                int i = adminMapper.deleteByPrimaryKey(aid);
                if (i>0){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 修改管理的密码或者其他
     * @param admin
     * @return
     */
    @Override
    public Boolean updateAdminPassword(Admin admin) {
        if (admin!=null){
            int i = adminMapper.updateByPrimaryKey(admin);
            if (i>0){
                log.info("修改成功");
                return true;
            }
            log.info("修改失败");
        }
        return false;
    }

    /**
     * 获取管理员的信息
     * @param aid
     * @return
     */
    @Override
    public Admin getAdminByRid(Integer aid) {
        if (aid!=null){
            Admin admin = adminMapper.selectByPrimaryKey(aid);
            log.info("获取信息成功");
            return admin;
        }
        return null;
    }
}
