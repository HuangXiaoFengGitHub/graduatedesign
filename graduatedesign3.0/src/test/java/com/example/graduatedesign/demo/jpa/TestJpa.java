package com.example.graduatedesign.demo.jpa;

import com.example.graduatedesign.GraduatedesignApplication;
import com.example.graduatedesign.Model.jpa.JpaUser;
import com.example.graduatedesign.dao.jpa.JpaUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * jpa操作mysql对象增删改查操作测试
 *
 * @author pysasuke
 * @create 2017-09-15
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GraduatedesignApplication.class)
public class TestJpa {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    public void testInsert() {
        long countBefore =jpaUserRepository.count();
        JpaUser jpaUser = generateJpaUser();
        log.info(jpaUser.toString());
        jpaUserRepository.save(jpaUser);
        long countAfter =jpaUserRepository.count();
        Assert.assertEquals(++countBefore,countAfter);
    }

    @Test
    public void testSelect() {
        JpaUser jpaUser = generateJpaUser();
        /*
        插入成功后id会有值(mysql数据库生成的)
        前提是：
        1.数据库主键设置为自动增长
        2.实体对象主键属性上有注解@Id和@GeneratedValue
         */
        jpaUserRepository.save(jpaUser);
        JpaUser selectedJpaUser = jpaUserRepository.findUserById(jpaUser.getId());
        Assert.assertNotNull(selectedJpaUser);
        Assert.assertEquals(jpaUser, selectedJpaUser);
    }

    @Test
    public void testUpdate() {
        JpaUser jpaUser = generateJpaUser();
        jpaUserRepository.save(jpaUser);
        Optional<JpaUser> selectedJpaUser = jpaUserRepository.findById(jpaUser.getId());
        Assert.assertNotNull(selectedJpaUser);
        selectedJpaUser.get().setPassword("654321");
        //保存修改后的对象，相当于update操作
        jpaUserRepository.save(selectedJpaUser.get());
        //findOne(默认实现)方法与findUserById(自己实现)都是根据id查找
        JpaUser updatedJpaUser = jpaUserRepository.findUserById(selectedJpaUser.get().getId());
        Assert.assertNotNull(updatedJpaUser);
        Assert.assertEquals(selectedJpaUser.get().getPassword(), updatedJpaUser.getPassword());
    }

    @Test
    public void testDelete() {
        JpaUser jpaUser = generateJpaUser();
        jpaUserRepository.save(jpaUser);

        jpaUserRepository.deleteById(jpaUser.getId());
        JpaUser selectedJpaUser = jpaUserRepository.findUserById(jpaUser.getId());
        Assert.assertNull(selectedJpaUser);
    }

    private JpaUser generateJpaUser() {
        JpaUser jpaUser = new JpaUser();
        jpaUser.setUsername("zhangsan");
        jpaUser.setPassword("123456");
        jpaUser.setMobile("13666666666");
        jpaUser.setEmail("123456@qq.com");
        return jpaUser;
    }
}
