package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.enums.ActivityState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Activity findByActivityId(Long id);
    //分页查询活动，可输入的条件有：活动类别，活动标签，活动状态，组织名称，活动时间等等
    List<Activity> findActivitiesByCategory(ActivityCategory category);
    List<Activity> findActivitiesByStatus(ActivityState stateEnum);
    List<Activity> findActivitiesByOrganization(Organization organization);
    List<Activity> findActivitiesByStartTimeAndEndTime(Calendar startTime,Calendar endTime);
}
