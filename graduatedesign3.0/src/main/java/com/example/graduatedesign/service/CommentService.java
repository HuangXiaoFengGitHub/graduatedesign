package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityComment;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.Model.UserActivityComment;
import com.example.graduatedesign.dao.ActivityCommentRepository;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.UserActivityCommentRepository;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.service.serviceImp.CommentServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@Slf4j
public class CommentService implements CommentServiceImp {
    @Autowired
    UserActivityCommentRepository userActivityCommentRepository;
    @Autowired
    ActivityCommentRepository activityCommentRepository;
    @Autowired
    UserRepostory userRepostory;
    @Autowired
    ActivityRepository activityRepository;
    public boolean postComment(String content,long userId,long activityId)
    {
        log.info("post comment:");
        if(!StringUtils.isNotBlank(content))
        {
            return false;
        }
        ActivityComment activityComment=ActivityComment.builder().content(content).createTime(Calendar.getInstance()).build();
        ActivityComment activityComment1=activityCommentRepository.saveAndFlush(activityComment);
        User user=userRepostory.findByUserId(userId);
        Activity activity=activityRepository.findByActivityId(activityId);
        UserActivityComment userActivityComment=UserActivityComment.builder().user(user).activityComment(activityComment1).activity(activity).createTime(Calendar.getInstance()).build();
        if(userActivityCommentRepository.saveAndFlush(userActivityComment)!=null)
            return true;
        else
            return false;
    }
}
