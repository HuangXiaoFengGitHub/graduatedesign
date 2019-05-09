package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.enums.ActivityState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Calendar;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long>, JpaSpecificationExecutor<Activity> {
    int countActivityByOrganizationAndStatus(Organization organization,int status);
    int countActivityByOrganizationAndStatusGreaterThan(Organization organization,int status);
    int countActivityByOrganizationAndStatusBetween(Organization organization,int status1,int status2);
    int countActivityByOrganization(Organization organization);
    Activity findByActivityId(Long id);
    //分页查询活动，可输入的条件有：活动类别，活动标签，活动状态，组织名称，活动时间等等
    Page<Activity> findActivitiesByCategory(ActivityCategory category, Pageable pageable);
    List<Activity> findActivitiesByOrganizationAndStatus(Organization organization,int status);
    List<Activity> findActivitiesByStatus(ActivityState stateEnum);
    List<Activity> findActivitiesByOrganization(Organization organization);
    List<Activity> findActivitiesByOrganizationOrderByStatusAsc(Organization organization);
    List<Activity> findActivitiesByStartTimeAndEndTime(Calendar startTime,Calendar endTime);
    Long countActivityByCategory(ActivityCategory category); //统计
    Long countActivityByStatus(ActivityState state); //统计
    Activity findByActivityName(String activityName);
}
