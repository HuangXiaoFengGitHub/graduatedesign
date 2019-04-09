package com.example.test1.demo.service;

import com.example.test1.demo.Model.User;
import com.example.test1.demo.dao.UserRepostory;
import com.example.test1.demo.service.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    UserRepostory userRepostory;
    public User findUserByName(String name)
 {
     return userRepostory.findByUserName(name);
 }
    public void save(User user)
    {
        userRepostory.save(user);
    }
    @Override
    public List<User> getUserList() {
        return userRepostory.findAll();
    }
}
