package com.example.graduatedesign.demo.util;


import com.example.graduatedesign.Model.JacksonUser;
import com.example.graduatedesign.demo.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
public class JacksonTest extends BaseTest {
    @Autowired
    ObjectMapper mapper ;
    @Test
    public void test2() throws Exception {
        JacksonUser friend = JacksonUser.builder().id("1").pwd("19960704").userId("1996").build();
        //写为字符串
        String text = mapper.writeValueAsString(friend);
        // 写为文件
        mapper.writeValue(new File("friend.json"), friend);
        // 写为字节流
        byte[] bytes = mapper.writeValueAsBytes(friend);
        System.out.println(text);
        // 从字符串中读取
        JacksonUser newFriend = mapper.readValue(text, JacksonUser.class);
        // 从字节流中读取
        newFriend = mapper.readValue(bytes, JacksonUser.class);
        // 从文件中读取
        newFriend = mapper.readValue(new File("friend.json"), JacksonUser.class);
        System.out.println(newFriend);
    }
}
