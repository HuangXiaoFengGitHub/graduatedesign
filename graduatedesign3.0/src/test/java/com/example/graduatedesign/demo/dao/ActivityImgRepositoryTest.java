package com.example.graduatedesign.demo.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityImg;
import com.example.graduatedesign.dao.ActivityImgRepository;
import com.example.graduatedesign.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ActivityImgRepositoryTest {
    @Autowired
    ActivityImgRepository activityImgRepository;
    @Test
    public void test()
    {
        Activity activity= Activity.builder().activityId(2L).build();
        List<ActivityImg> activityImgList=activityImgRepository.findActivityImgsByActivity(activity);
        log.info(activityImgList.toString());
        Assert.assertEquals(2,activityImgList.size());
    }
    @Test
    public void test2()
    {
        String test= MD5.getMd5("a19960704");
        log.info(test);
        log.info(MD5.getMd5(test));
    }
}
