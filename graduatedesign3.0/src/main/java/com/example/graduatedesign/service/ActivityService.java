package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.dao.ActivityCategoryRepository;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.TagsRepository;
import com.example.graduatedesign.enums.ActivityState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ActivityService {
    @Autowired
    ActivityCategoryRepository activityCategoryRepository;
    @Autowired
    TagsRepository tagRepository;
    @Autowired
    ActivityRepository activityRepository;
    //分页查询活动，可输入的条件有：活动类别，活动标签，活动状态，组织名称，活动时间等等
    public Set<Activity> findActivityByCategory(String category)
    {
        return activityCategoryRepository.findByActivityCategoryName(category).getActivities();
    }
    public Set<Activity> findActivityByTags(String tags)
    {
        return tagRepository.findByTagName(tags).getActivities();
    }
    public List<Activity> findActivityByState(ActivityState state)
    {
        return  activityRepository.findActivitiesByStatus(state);
    }
    public List<Activity> findActivityByOrganization(Organization organization)
    {
        return activityRepository.findActivitiesByOrganization(organization);
    }
    public List<Activity> findActivityByTime(Calendar startTime, Calendar endTime)
    {
        return activityRepository.findActivitiesByStartTimeAndEndTime(startTime,endTime);
    }
}
