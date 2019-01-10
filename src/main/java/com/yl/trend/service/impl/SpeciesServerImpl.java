package com.yl.trend.service.impl;

import com.yl.trend.entity.Species;
import com.yl.trend.mapper.SpeciesMapper;
import com.yl.trend.service.SpeciesServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class SpeciesServerImpl implements SpeciesServer {

    @Autowired
    private SpeciesMapper speciesMapper;

    @Override
    public List<Species> FindSpeciess() {

            List<Species> speciess = speciesMapper.findAll();//

          return speciess;
    }

    @Override
    public Boolean addSpecies(Species species) {
        if (species!=null){
            int i = speciesMapper.insert(species);
            log.info("添加成功");
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteSpecies(Integer sid) {
        if (sid!=null){
            int i = speciesMapper.deleteByPrimaryKey(sid);
            if (i>0){
                log.info("删除成功");
            }
            return true;
        }
        return false;
    }
}
