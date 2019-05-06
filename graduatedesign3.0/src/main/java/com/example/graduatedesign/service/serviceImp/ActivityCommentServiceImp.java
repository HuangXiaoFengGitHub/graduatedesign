package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.UserActivityComment;

import java.util.List;

public interface ActivityCommentServiceImp {
    public List<UserActivityComment>  findCommentByActivity(long activityId,int pageIndex,int pageSize);
}
