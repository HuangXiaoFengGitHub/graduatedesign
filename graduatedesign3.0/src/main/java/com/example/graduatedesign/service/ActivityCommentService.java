package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityImg;
import com.example.graduatedesign.Model.UserActivityComment;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.UserActivityCommentRepository;
import com.example.graduatedesign.service.serviceImp.ActivityCommentServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityCommentService implements ActivityCommentServiceImp {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    UserActivityCommentRepository userActivityCommentRepository;
    /**
     * 分页查找该活动下的评论
     * @param pageIndex
     * @param pageSize
     * @param activityId
     * @return
     */
    public List<UserActivityComment> findCommentByActivity(long activityId,int pageIndex,int pageSize)
    {
        if(pageIndex >=0 && pageSize>0 && activityId>0)
        {
            Sort sort=new Sort(Sort.Direction.DESC,"createTime");
            Pageable pageable= PageRequest.of(pageIndex,pageSize,sort);
            Activity activity=activityRepository.findByActivityId(activityId);
            Page<UserActivityComment> userActivityComments=userActivityCommentRepository.findByActivity(activity,pageable);
            return userActivityComments.getContent();
        }
        else {
            return null;
        }
    }
}
