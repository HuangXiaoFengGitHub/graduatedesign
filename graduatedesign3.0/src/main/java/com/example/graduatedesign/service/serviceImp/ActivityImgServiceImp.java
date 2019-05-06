package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityImg;

import java.util.List;

public interface ActivityImgServiceImp {
    public List<ActivityImg> findAll(Activity activity);
}
