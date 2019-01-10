package com.yl.trend.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yl.trend.entity.Logger;
import com.yl.trend.entity.Resume;
import com.yl.trend.entity.Species;
import com.yl.trend.entity.User;
import com.yl.trend.service.LoggerService;
import com.yl.trend.service.ResumeService;
import com.yl.trend.service.SpeciesServer;
import com.yl.trend.service.UserService;
import com.yl.trend.utils.Const;
import com.yl.trend.utils.LoggerUtils;
import com.yl.trend.utils.TimeUtils;
import com.yl.trend.wxcontroller.WXLoginController;
import com.yl.trend.wxutils.IMoocJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 简历的操作
 */
@Slf4j
@Controller
@RequestMapping("/resume/")
public class ResumeController{

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SpeciesServer speciesServer;
    @Autowired
    private UserService userService;

    @Autowired
    private WXLoginController wxLoginController;
    /**
     * 记录
     */
    @Autowired
    private LoggerService loggerService;



    /**
     * 模糊查询
     * @param
     * @param
     * @return //测试通过
     * @throws Exception
     */
    @RequestMapping(value = "list/resumeName",method = RequestMethod.GET)
    @ResponseBody
    public String resumeList(String rname)throws Exception{

        Set<Resume> resumes = resumeService.getRnameSet(rname);

       return jsonStr(resumes);
    }

    /**
     * 简历详情(detail)
     * @param rid
     * @param
     * @return
     */
    @RequestMapping("detail/{rid}")
    public Map<String,Object> getResume(@PathVariable("rid") Integer rid){
        Resume resume = resumeService.getResumeInfo(rid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("cost",resume.getScost());
            map.put("resumeImg",resume.getSimage());

        return map;
    }

    /**
     * 后台管理的简历列表
     * @param
     * @return
     */
    @RequestMapping(value = "resumelist",method = RequestMethod.GET)
    public String testPageNum(Resume resume,Model model){
       // PageHelper.startPage(2,5);//每次显示10页
        List<Resume> resumelist = resumeService.getResumeList();
        PageInfo<Resume> pageInfo = new PageInfo<Resume>(resumelist);
        model.addAttribute("resume",resume);
        model.addAttribute("pageInfo",pageInfo);
        return "/fileoperation/filelist";
    }
    /**
     * 上传图片
     *
     * @param upload
     * @return
     */
    @RequestMapping("file/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile upload) {
        //定义后缀名称数组
        String arrays[] = {"jpg", "jepg", "ico", "bmp", "png"};
        return compare(arrays, upload);
    }

    /**
     * 上传Word文档
      * @param uploadWord
     * @return
     */
    @RequestMapping(value = "file/uploadWord",method = RequestMethod.POST)
    @ResponseBody
    public String uploadWord(@RequestParam MultipartFile uploadWord) {

        String arrays[]={"doc","docx"};

        return compare(arrays, uploadWord);
    }

    /**
     * 在线文本编辑器（富文本编辑）上传图片
     * @param upload
     * @param response 没测试好!
     * @param request
     */
    @RequestMapping("uploadfile")
    public void uploadfile(@RequestParam MultipartFile upload, HttpServletResponse response,
                           HttpServletRequest request) {

        try {
            String url = resumeService.doPutFile(upload);

            PrintWriter out = response.getWriter();
            String callBack = request.getParameter("CKEditorFuncNum");
            out.println("<script>window.parent.CKEDITOR.tools.callFunction(" + callBack + ",'" + url + "')</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 图片文件添加到数据中的
     * @param resume
     * @param model
     * @return
     */
   @RequestMapping("add")
    public  String  addResume(Resume resume, Model model){

            boolean flag = resumeService.addResume(resume);
            if (flag){
                model.addAttribute("info","添加"+resume.getRname()+"成功");
            }else {
                model.addAttribute("info", "添加" + resume.getRname() + "失败");
            }
        return "/resume_info";
    }

    /**
     * 附件文件
     * @param //测试通过
     * @param
     * @return 提供的是一个假的下载
     * @throws Exception
     */
       @RequestMapping(value = "downloadfile",method = RequestMethod.GET)
       @ResponseBody
       public String downLoadFile(String rid,String openId){
            //开始的时间

           //当前系统用户

           Integer t=Integer.parseInt(rid);
           Resume resumes = resumeService.getResumeInfo(t);
           User user = userService.findByOpenId(openId);

                if (resumes.getUid().equals(user.getUid())){
                    return jsonStr(IMoocJSONResult.build(400,"模板存在,请别重复下载",resumes));
                }else{
                    resumes.setUid(user.getUid());
                    resumeService.updateResumeByRid(resumes);

                    //拿到简历中的价格cost
                    BigDecimal cost = resumes.getScost();
                    Logger logger = new Logger();
                    logger.setAccountExpend(""+cost);
                    logger.setTransactionEndtime(TimeUtils.DateFormat());
                    logger.setTransactionMark(LoggerUtils.TRANSACTIONMARK_YES);
                    logger.setUid(user.getUid());
                    //进行添加记录数据
                    loggerService.addLoggerInfo(logger);
                }
           //表示执行完成之后就开始插入数据库信息

           return jsonStr(resumes);
       }

    /**
     * 请求之前判断用户是否有这个数据
     * @param
     * @return
     */
        @RequestMapping("mark")
        @ResponseBody
        public String viewUserResume(String openId,Integer rid){
            User user = userService.findByOpenId(openId);
            Resume resumes = resumeService.getResumeInfo(rid);
            if (user.getUid().equals(resumes.getUid())){
                log.info("这个模板存在");

                return "mark";
            }
           return "mark_no";
       }


    /**
     * 控制上传文件乱
     * @param arr
     * @param file
     */
    private String compare(String arr[],MultipartFile file) {


        String fileName = file.getOriginalFilename();

            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String url = null;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].indexOf(suffixName)!=-1) {
                    System.out.println(arr[i]);

                    url = resumeService.doPutFile(file);

                    return url;

                }else{

                    System.out.println("你输入的文件不合法");
                    return null;
                }
            }
            return null;

    }

    /**
     * 模板信息详情
     * @return //测试通过
     */
    @RequestMapping(value = "resumeinfo",method = RequestMethod.GET)
    @ResponseBody
    public String resumeInfo(String rid){

        Integer t=Integer.parseInt(rid);
        Resume resumes = resumeService.getResumeInfo(t);

        return jsonStr(resumes);
    }
    /**
     * 会员列表的简历
     * //测试通过
     * @return
     */
    @RequestMapping("viprsumelist")
    @ResponseBody
    public String vipResumeList(){
        Set<Resume> resumes = resumeService.getResumeInfoList();
       for (int i=0;i<resumes.size();i++){
           if (i==6){
               break;
           }
       }
        return jsonStr(resumes);
    }

    /**
     * 讲对象转成json工具
     * @param object
     * @return
     */
    private String jsonStr(Object object){
        String objectjson=JSONObject.toJSON(object).toString();
        return  objectjson;
    }

    /**
     * 删除简历
     * @param rid
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteresume/{rid}",method = RequestMethod.GET)
    public String deleteResume(@PathVariable("rid") Integer rid,Model model){
        Boolean mark = resumeService.deleteResumeByRid(rid);
        if (mark){
            model.addAttribute("info","删除成功");
        }else{
            model.addAttribute("info","删除失败");
        }
        return "/fileoperation/resume_info";
    }

    /**
     * 加载修改的页面 暂时不用 后期使用
     * @return
     */
    @RequestMapping("loadupdate_resume/{rid}")
    public String loaddUpdate(Resume resume,Model model){
        Resume resumes = resumeService.getResumeById(resume.getRid());
        List<Species> species = speciesServer.FindSpeciess();
        if (species!=null){
            model.addAttribute("species",species);
        }
        model.addAttribute("resume",resumes);
        return "/fileoperation/resume_upload";
    }

    /**
     * 修改简历 全部修改
     */
    @RequestMapping(value = "updateresume2",method = RequestMethod.POST)
    public String updateResume(Resume resume,Model model){
        Resume re = resumeService.getResumeById(resume.getRid());
          re.setRname(resume.getRname());
           re.setVip(resume.getVip());
            re.setRaddress(resume.getRaddress());
            re.setSimage(resume.getSimage());
            re.setSpecies(resume.getSpecies());
            re.setScost(resume.getScost());
        Boolean mark = resumeService.updateResumeByRid(resume);
        if (mark){
            model.addAttribute("info","更新成功");
        }else{
            model.addAttribute("info","更新失败");
        }
        return "/fileoperation/resume_info";
    }



    /**
     * 修改简历 设置VIP
     */
    @RequestMapping(value = "updateresume/{rid}",method = RequestMethod.GET)
    public String updateResumeByRid(@PathVariable("rid") Integer rid,Model model){
        Resume resume = resumeService.getResumeInfo(rid);
            if (resume.getVip()==0){
                resume.setVip(Const.VIP_YES);
            }else if(resume.getVip()==1){
                resume.setVip(Const.VIP_NO);
            }
        Boolean mark = resumeService.updateResumeByRid(resume);
        if (mark){
            model.addAttribute("info","设置成功");
        }else{
            model.addAttribute("info","设置失败");
        }
        return "/fileoperation/resume_info";
    }



}
