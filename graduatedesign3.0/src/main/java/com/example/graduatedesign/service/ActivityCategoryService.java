package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.dao.ActivityCategoryRepository;
import com.example.graduatedesign.service.serviceImp.ActivityCategoryServiceImp;
import com.example.graduatedesign.service.serviceImp.ActivityServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ActivityCategoryService implements ActivityCategoryServiceImp {
    @Autowired
    ActivityCategoryRepository activityCategoryRepository;
    public List<ActivityCategory> findAll() {
        return activityCategoryRepository.findAll();
    }
   public Optional<ActivityCategory> findById(long id){
        return activityCategoryRepository.findById(id);
    }
    public List<ActivityCategory> findFirstLever()
    {
        return activityCategoryRepository.findActivityCategoriesByParentId(0L);
    }

}
