package com.yl.trend.service;

import com.yl.trend.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ResumeService {

    public List<Resume> getResumeList(Map<String, Object> result);

    public List<Resume> getResumeList();

    public Long getResumeCount(Resume resume);

    public Resume getResumeById(Integer rid);

    public boolean addResume(Resume resume);

    public String doPutFile(MultipartFile file);

    public void downLoadFile(Resume resume);

    public Resume getResumeInfo(Integer rid);

    public Set<Resume> getResumeInfoList();

    Set<Resume> getRnameSet(String rname);


    Boolean deleteResumeByRid(Integer rid);

    Boolean updateResumeByRid(Resume resume);

    Boolean updateAll(Resume resume);


}