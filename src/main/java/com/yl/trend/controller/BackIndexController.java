package com.yl.trend.controller;

import com.yl.trend.entity.Admin;
import com.yl.trend.entity.Species;
import com.yl.trend.service.AdminService;
import com.yl.trend.service.SpeciesServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/back/")
public class BackIndexController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SpeciesServer speciesServer;
    //跳转到登录页面
    @RequestMapping("login")
    public String index(){
        return "/login";
    }


    @RequestMapping(value = "backindex",method = RequestMethod.POST)
    public String login(Admin admin, HttpSession session){
        if (admin!=null){
            Admin admins = adminService.findNameAndPassword(admin);
            if (admin.equals(admins)){
                session.setAttribute("admin",admin);

                return  "/index";
            }else {
                return "/login";
            }
        }
        return "/login";
    }

    /**就是加载到添加文件的页面
     * 后台添加文件操作
     * @return
     */
    @RequestMapping("file/backindex")
    public String backIndex(Model model){
        List<Species> species = speciesServer.FindSpeciess();
        if (species!=null){
            model.addAttribute("species",species);
        }
        return "/fileoperation/backfileupload";
    }

    @RequestMapping("hello")
    public String hello(){
        return "/back/main";
    }


}
