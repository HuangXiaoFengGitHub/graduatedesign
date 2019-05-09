package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceServiceImp {
   List<Place> findAll();
   Place findByPlaceName(String name);
   Place findByStatus(int status);
}
