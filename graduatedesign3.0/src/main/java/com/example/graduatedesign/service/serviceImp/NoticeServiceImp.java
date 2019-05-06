package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Notice;

import java.util.List;

public interface NoticeServiceImp {
    public Notice findByNoticeId(long noticeId);
    public List<Notice> findByActivity(long activityId);
}
