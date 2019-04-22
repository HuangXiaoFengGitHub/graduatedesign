package com.example.graduatedesign.demo.dao;


import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.demo.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Slf4j
public class UserRepositoryTest extends BaseTest {
    @Autowired
    UserRepostory userRepostory;
    @Autowired
    ActivityRepository activityRepository;
    @Test
    public void test()
    {
        userRepostory.deleteAll();
        User user= User.builder().phone("13538861967").build();
        userRepostory.save(user);
        User select=userRepostory.findByUserId(1L);

        Assert.assertEquals(user.getPhone(),select.getPhone());
    }
    //测试分页查询
    @Test
    public void testPageQuery() throws Exception {

    }
    @Test
    public void testManyToMany()
    {
       // Activity activity=Activity.builder().activityName("多对多测试").articleDesc("多对多测试1对1").build();
        Activity activity=activityRepository.findByActivityId(1L);
        User user=userRepostory.findByUserId(1L);
        log.info(user.getLikeActivities().toString());
        user.getLikeActivities().add(activity);
        log.info(user.getLikeActivities().toString());
        userRepostory.save(user);
        Activity newActivity=activityRepository.findByActivityName(activity.getActivityName());
        Assert.assertEquals(activity.getActivityName(),newActivity.getActivityName());
    }
}
