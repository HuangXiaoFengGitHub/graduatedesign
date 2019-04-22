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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void testPageFind()
    {
        ActivityCategory category=activityCategoryRepository.findByActivityCategoryId(2L);
      //  ActivityCategory category=new ActivityCategory();
     //   category.setActivityCategoryName("学术讲座");
        log.info(category.getActivityCategoryName());
        log.info(category.getActivityCategoryId()+"");
        log.info(category.getActivities().toString());
       // activityCategoryRepository.save(category);//手动持久化
        int page=2,size=2; //第2页，每一页有2个元素
        Sort sort= new Sort(Sort.Direction.DESC,"activityId");
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<Activity> activityList=activityRepository.findActivitiesByCategory(category,pageable);
        //List<Activity> activityList=activityRepository.findActivitiesByCategory(2L);
        log.info(activityList.toString());
        log.info(activityList.getTotalElements()+"");//一共查询出多少个元素
        log.info(activityList.getTotalPages()+""); //一共有多少页
        log.info(activityList.getNumber()+""); //当前页
        log.info(activityList.getContent().toString());//当前页内容
        log.info(activityList.getSize()+"");//当前页大小
        Assert.assertEquals(2,activityList.getNumber());
    }
    @Test
    public void testEdit()
    {
     //ActivityCategory category=activityCategoryRepository.findByActivityCategoryId(23L);
        ActivityCategory category=activityCategoryRepository.findByActivityCategoryName("黄晓峰");
        log.info(category.getActivityCategoryName());
        log.info(category.getActivityCategoryId()+"");
        category.setActivityCategoryName("黄晓峰2");
        activityCategoryRepository.save(category);
    }
    @Test
    public void testCount()
    {
        ActivityCategory category=activityCategoryRepository.findByActivityCategoryId(2L);
        long count=activityRepository.countActivityByCategory(category);
        Assert.assertEquals(2,count);
    }
}
