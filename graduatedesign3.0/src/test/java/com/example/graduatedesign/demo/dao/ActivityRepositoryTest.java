package com.example.graduatedesign.demo.dao;


import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.dao.ActivityCategoryRepository;
import com.example.graduatedesign.dao.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ActivityRepositoryTest {
    @Autowired
    ActivityRepository activityRepository ;
    @Autowired
    ActivityCategoryRepository activityCategoryRepository;
    @Test
    public void testSelect()
    {
        Activity activity=activityRepository.findByActivityId(1L);
        log.info(activity.toString());
        log.info(activity.getCategory().getActivityCategoryName());
        Assert.assertEquals(1L,activity.getActivityId());
    }
    @Test
    public void testFind()
    {
        ActivityCategory category=activityCategoryRepository.findByActivityCategoryId(2L);
      //  ActivityCategory category=new ActivityCategory();
        category.setActivityCategoryName("学术讲座");
        log.info(category.getActivityCategoryName());
        log.info(category.getActivityCategoryId()+"");
        log.info(category.getActivities().toString());
       // activityCategoryRepository.save(category);//手动持久化
        List<Activity> activityList=activityRepository.findActivitiesByCategory(category);
        //List<Activity> activityList=activityRepository.findActivitiesByCategory(2L);
         log.info(activityList.toString());

        Assert.assertEquals(2,activityList.size());
    }
    @Test
    public void testEdit()
    {
     //   ActivityCategory category=activityCategoryRepository.findByActivityCategoryId(23L);
        ActivityCategory category=activityCategoryRepository.findByActivityCategoryName("黄晓峰");
        log.info(category.getActivityCategoryName());
        log.info(category.getActivityCategoryId()+"");
        category.setActivityCategoryName("黄晓峰2");
        activityCategoryRepository.save(category);
    }
}
