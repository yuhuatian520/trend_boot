package com.yl.trend.service;

import com.yl.trend.entity.Admin;

import java.util.List;

public interface AdminService {

   Admin findNameAndPassword(Admin admin);


    Boolean addAdmins(Admin admin);

    List<Admin> findAll();

    List<Admin> queryAdminNameBlurry(String rname);

    Boolean deleteAdmin(Integer aid);

    Boolean updateAdminPassword(Admin admin);

 Admin getAdminByRid(Integer aid);
}
