package com.yl.trend.controller;


import com.alibaba.fastjson.JSONObject;
import com.yl.trend.entity.Species;
import com.yl.trend.service.ResumeService;
import com.yl.trend.service.SpeciesServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/species/")
public class SpeciesController {

    @Autowired
    private SpeciesServer speciesServer;

    @Autowired
    private ResumeService resumeService;


    @RequestMapping("list")
    @ResponseBody
    public String speciesList(){

        //Map<String, Object> hash = new HashMap<>();
        List<Species> speciesList = speciesServer.FindSpeciess();
       // List<Resume> resumeList = resumeService.getResumeList();

        //hash.put("speciesList",speciesList);
        //hash.put("resumeList",resumeList);

        List<Object> hash = new ArrayList<>();
            hash.add(speciesList);
            //hash.add(resumeList);
        //JSONObject jsonmap = new JSONObject(hash);
        Object json = JSONObject.toJSON(hash);
       return json.toString();

    }

    @RequestMapping("speciesls")
    public String speciesList(Model model){
        List<Species> speciesList = speciesServer.FindSpeciess();
         model.addAttribute("speciesList",speciesList);
         return "/species/species_list";
    }

    /**
     * 加载添加页面
     * @return
     */
    @RequestMapping(value = "loaddadd")
    public String loaddSpecies(){
        return "/species/species_add";
    }
    @RequestMapping(value = "addspecies",method = RequestMethod.POST)
    public String addspecies(Species species,Model model){
        Boolean mark = speciesServer.addSpecies(species);
        if (mark){
            model.addAttribute("info","添加成功");
        }else{
            model.addAttribute("info","添加失败");
        }
        return "/species/species_info";
    }

    @RequestMapping(value = "deletespecies/{sid}",method = RequestMethod.GET)
    public String addspecies(@PathVariable("sid")  Integer sid, Model model){
        Boolean mark = speciesServer.deleteSpecies(sid);
        if (mark){
            model.addAttribute("info","删除成功");
        }else{
            model.addAttribute("info","删除失败");
        }
        return "/species/species_info";
    }






}
