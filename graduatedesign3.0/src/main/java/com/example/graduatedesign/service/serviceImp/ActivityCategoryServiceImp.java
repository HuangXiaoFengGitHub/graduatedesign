package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Headline;

import java.util.List;
import java.util.Optional;

public interface ActivityCategoryServiceImp {
    List<ActivityCategory> findAll();
    Optional<ActivityCategory> findById(long id);
}
