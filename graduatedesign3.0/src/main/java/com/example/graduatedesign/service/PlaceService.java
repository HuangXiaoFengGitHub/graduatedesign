package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Place;
import com.example.graduatedesign.dao.PlaceRepository;
import com.example.graduatedesign.service.serviceImp.PlaceServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlaceService implements PlaceServiceImp {
    @Autowired
    PlaceRepository placeRepository;
    public List<Place> findAll(){
        return placeRepository.findAll();
    }
    public Place findByPlaceName(String name){
        return placeRepository.findByPlaceName(name);
    }
    public Place findByStatus(int status){
        return placeRepository.findByStatus(status);
    }
}
