package com.yl.trend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yl.trend.entity.Admin;
import com.yl.trend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/back/admin/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 跳转到添加管理员列表
     * @return
     */
    @RequestMapping("loadAdd")
    public String loadAdd(){
        return "/back/adminadd";
    }

    /**
     * 加载修改的页面 先生获取到rid
     * @param admin
     * @param model
     * @return
     */
    @RequestMapping("loaddUpdate")
    public String loaddUpdate(Admin admin,Model model){

        Admin admininfo = adminService.getAdminByRid(admin.getAid());
        if (!"".equals(admininfo)){
            model.addAttribute("admininfo",admininfo);
        }
        return "/back/admin_update";
    }

    /**
     * 添加后台管理员
     * @param admin
     * @param model
     * @return
     */
    @RequestMapping(value = "insertadmin",method = RequestMethod.POST)
    public String addAdmins(Admin admin,Model model){

                Boolean mark = adminService.addAdmins(admin);
                if (mark) {
                    model.addAttribute("info", "添加管理" + admin.getAname() + "成功");
                }else{
                model.addAttribute("info","添加管理"+admin.getAname()+"失败");
            }
        return "/back/admin_info";
    }

    /**
     * 显示出管理员列表
     * @param model
     * @param
     * @return
     */
    @RequestMapping(value = "adminlist",method = RequestMethod.GET)
    public String viewListAdmin(Model model){

        PageHelper.startPage(1,100);
        List<Admin>  admins = adminService.findAll();
        PageInfo<Admin> pageinfo = new PageInfo<>(admins);
        model.addAttribute("admins",admins);
        model.addAttribute("pageinfo",pageinfo);
        return "/back/admin_list";
    }

    /**
     * 跟id编辑管理员的密码
     * @return admin
     */
    @RequestMapping(value = "updateAdmin",method = RequestMethod.POST)
    public String editAdminInfo(Admin admin,Model model){
        if (admin!=null){
            Boolean mark = adminService.updateAdminPassword(admin);
            if (mark){
                model.addAttribute("info","修改"+admin.getAname()+"成功");
            }else {
                model.addAttribute("info","修改"+admin.getAname()+"失败");
            }
        }
        return "/back/admin_info";
    }

}
