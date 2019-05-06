package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.Notice;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.NoticeRepository;
import com.example.graduatedesign.service.serviceImp.NoticeServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoticeService implements NoticeServiceImp {
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    ActivityRepository activityRepository;
    public Notice findByNoticeId(long noticeId)
    {
        return noticeRepository.findNoticeByNoticeId(noticeId);
    }
    public List<Notice> findByActivity(long activityId)
    {
        Activity activity=activityRepository.findByActivityId(activityId);
        if(activity!=null)
            return noticeRepository.findNoticesByActivity(activity);
        else
            return null;
    }
}
