package com.yl.trend.service;

import com.yl.trend.entity.Species;

import java.util.List;


public interface SpeciesServer {

    public List<Species> FindSpeciess();


    Boolean addSpecies(Species species);

    Boolean deleteSpecies(Integer sid);
}
