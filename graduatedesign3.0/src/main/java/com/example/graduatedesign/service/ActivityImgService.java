package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityImg;
import com.example.graduatedesign.dao.ActivityImgRepository;
import com.example.graduatedesign.service.serviceImp.ActivityImgServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityImgService implements ActivityImgServiceImp {
    @Autowired
    ActivityImgRepository activityImgRepository;
    public List<ActivityImg> findAll(Activity activity)
    {
        Sort sort=Sort.by(Sort.Direction.DESC,"priority");
        return activityImgRepository.findActivityImgsByActivity(activity);
    }
}
