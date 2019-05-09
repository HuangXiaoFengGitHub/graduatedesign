package com.example.graduatedesign.dao;


import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepostory extends JpaRepository<User,Long> {
    User findByUserId(long id);
    User findByUserName(String name);
    User findByNickName(String nickName);
    User findByEmail(String email);
    //List<User> findByUserName(String name);
    User findByUserNameAndPassword(String username,String password);
    User findByEmailAndPassword(String email,String password);
    User findByUserNameAndIsManager(String username,int isManager);
}
