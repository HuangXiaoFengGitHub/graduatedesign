package com.example.graduatedesign.demo.mongo;


import com.example.graduatedesign.GraduatedesignApplication;
import com.example.graduatedesign.Model.mongo.MgUser;
import com.example.graduatedesign.dao.mongo.MongoUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * mongodb测试
 *
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GraduatedesignApplication.class)
public class TestMongo {
    @Autowired
    private MongoUserRepository userRepository;

    @Test
    public void testMonogo() {
        MgUser mgUser = MgUser.builder().mgid(2L).nickname("py2").phone("1234").build();
        List<String> tags=new ArrayList<>();
        tags.add("hxf");
        tags.add("hxffff");
        //mgUser.setMgid(1L);
        mgUser.setTags(tags);
        //mgUser.setNickname("p");
        mgUser.setPhone("18650140605");
        //插入成功后_id属性有值(mongo数据库生成的)
        userRepository.insert(mgUser);
//        MgUser mgUsers=userRepository.findByNickname("py2");
  //      log.info(mgUsers.toString());
        //该方法使用的是mongodb中ObjectId为条件的
//        MgUser selectMgUser = userRepository.findOne(1L);
//        MgUser selectMgUser = userRepository.findByNickname("pysasuke");
        List<MgUser> selectMgUser = userRepository.findByMgid(2L); //转换为对象
        log.info(selectMgUser.toString());
        log.info(selectMgUser.get(1).getTags().toString());
        Assert.assertEquals(mgUser.getMgid(), selectMgUser.get(1).getMgid());
    }
}
