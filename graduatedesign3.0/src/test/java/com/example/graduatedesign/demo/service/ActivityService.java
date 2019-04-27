package com.example.graduatedesign.demo.service;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.dao.ActivityCategoryRepository;
import com.example.graduatedesign.demo.BaseTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ActivityService {
    @Autowired
    ActivityCategoryRepository activityCategoryRepository;
    ActivityService activityService=new ActivityService();
    @Test
    public void test()
    {
        ActivityCategory activityCategory=activityCategoryRepository.findByActivityCategoryId(2L);

    }
}
