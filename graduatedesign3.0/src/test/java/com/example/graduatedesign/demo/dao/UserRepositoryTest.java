package com.example.graduatedesign.demo.dao;


import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.demo.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
