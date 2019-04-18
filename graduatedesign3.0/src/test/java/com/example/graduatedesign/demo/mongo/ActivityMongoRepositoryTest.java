package com.example.graduatedesign.demo.mongo;


import com.example.graduatedesign.Model.mongo.ActivityMongo;
import com.example.graduatedesign.dao.mongo.ActivityMongoRepository;
import com.example.graduatedesign.demo.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ActivityMongoRepositoryTest extends BaseTest {
    @Autowired
    ActivityMongoRepository activityMongoRepository;
    @Test
    public void test() {
        ArrayList<String> notice = new ArrayList<>();
        notice.add("通知：今日闭幕");
        notice.add("通知：今日结束所有演出");
        ArrayList<String> tags=new ArrayList<>();
        tags.add("篮球");
        tags.add("体育");
        ActivityMongo activityMongo = ActivityMongo.builder().notices(notice).activityId(1L).tags(tags).build();
        activityMongoRepository.save(activityMongo);
        ActivityMongo activityMongo12=activityMongoRepository.findByActivityId(1L);
        Assert.assertEquals(activityMongo.getActivityId(),activityMongo12.getActivityId());
        Assert.assertEquals(activityMongo.getTags(),activityMongo12.getTags());
        }
    }
