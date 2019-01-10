package com.yl.trend.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.yl.trend.entity.Resume;
import com.yl.trend.mapper.ResumeMapper;
import com.yl.trend.service.ResumeService;
import com.yl.trend.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Slf4j
@Service
public class ResumeServiceImpl  implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public List<Resume> getResumeList() {
        List<Resume> resumelist = resumeMapper.getResumeList();
        return resumelist;
    }

    /**
     * 测试分页插件的事例 pagehelper
     * @param result
     * @return
     */
    @Override
    public List<Resume> getResumeList(Map<String,Object> result) {

        /*List<Resume> resumes = resumeMapper.getResumeList();
        return resumes;*/
        return null;
    }

    @Override
    public Long getResumeCount(Resume resume) {

        return resumeMapper.getResumeCount(resume);
    }

    /**
     * 通过id查询出简历详情
     * @param rid
     * @return
     */
    @Override
    public Resume getResumeById(Integer rid) {
        Resume resume = resumeMapper.selectByPrimaryKey(rid);
        return resume;
    }



    /**
     *  保存简历模板
     * @param resume
     * @return
     */
    @Override
    public boolean addResume(Resume resume) {

        if (!"".equals(resume.getSimage())&&!"".equals(resume.getRaddress())){
            resume.setVip(Const.VIP_NO);
            int count = resumeMapper.insertSelective(resume);
            if (count>0){
                return true;
        }
        }
        return false;
    }


    /**
     * 上传文件resume图片
     * @param file
     * @return
     */
    @Override
    public String doPutFile(MultipartFile file) {

        //图片名称生成器
        String filename = file.getOriginalFilename();
        String url = FileNameUtils.getFileNewName(Const.FILE_SERVER_URL, filename);

        System.out.println(url);
        //jersey 客户端
        Client client = new Client();
        WebResource resource = client.resource(url);
        try {

            //将文件转为byte[]
            byte[] buf = file.getBytes();

            //发送
            resource.put(String.class, buf);

            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 下载文件
     * @param resume
     * @return
     */
    public void downLoadFile(Resume resume) {
            if (resume!=null){
                try {
                    FileUtils.copyURLToFile(new URL(resume.getRaddress()),new File(resume.getRname()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }

    /**
     * 获取模板的信息
     * @param rid
     * @return
     */
    @Override
    public Resume getResumeInfo(Integer rid) {
        if (rid!=null){
            Resume resume = resumeMapper.findOne(rid);

            return resume;
        }
        return null;
    }

    /**
     * 根据VIP来查询
     * @param
     * @return
     */
    @Override
    public Set<Resume> getResumeInfoList() {
            Set<Resume> resumes = resumeMapper.getResumeInfo();
            return resumes;

    }

    /**
     * 根据名称进行模糊查询
     * @param rname
     * @return
     */
    @Override
    public Set<Resume> getRnameSet(String rname) {
        if (rname!=null&&rname!=""){
            Set<Resume> renames = resumeMapper.getResumeNameBlurry(rname);
            return renames;
        }
        return null;
    }

    /**
     * 删除简历操作
     * @param rid
     * @return true 删除成功 反之
     */
    @Override
    public Boolean deleteResumeByRid(Integer rid) {
        if (rid!=null){
            int mark = resumeMapper.deleteByPrimaryKey(rid);
            if (mark>0){
                log.info("删除成功");
                return true;
            }
        }
        return false;
    }

    /**
     * 修改简历
     * @param resume
     * @return
     */
    @Override
    public Boolean updateResumeByRid(Resume resume) {
        if (resume!=null){
            int mark = resumeMapper.updateByPrimaryKeySelective(resume);
            if (mark>0){
                log.info("修改成功");
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateAll(Resume resume) {
        if (resume!=null){
            resume.setVip(resume.getVip());
            resume.setUid(resume.getUid());
            int mark = resumeMapper.updateByPrimaryKey(resume);
            if (mark>0){
                log.info("修改成功");
                return true;
            }
        }
        return false;
    }
}




































