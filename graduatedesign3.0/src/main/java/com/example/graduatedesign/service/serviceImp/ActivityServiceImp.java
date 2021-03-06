package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.enums.ActivityState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface ActivityServiceImp {
    public long save(Activity activity);
    //分页查询活动，可输入的条件有：活动类别，活动标签，活动状态，组织名称，活动时间等等
    public Set<Activity> findActivityByCategory(String category);

    public Set<Activity> findActivityByTags(String tags);

    public List<Activity> findActivityByState(ActivityState state);

    public List<Activity> findActivityByOrganizationName(Organization organization);

    public List<Activity> findActivityByTime(Calendar startTime, Calendar endTime);

    public ActivityExecution addActivity(Activity activity, MultipartFile activityImg, List<MultipartFile> activityImgs);

    public Activity findActivityById(long id);

    public ActivityExecution findByCategoryPage(ActivityCategory activityCategory, int pageIndex, int pagesize);

    public ActivityExecution findSearch(Activity model,int pageIndex,int pageSize,String search);
    public ActivityExecution findActivityByOrganization(long organizationId);

}
