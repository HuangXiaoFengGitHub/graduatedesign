package com.example.test1.demo.service.serviceImp;

import com.example.test1.demo.Model.User;

import java.util.List;

public interface UserServiceImp {
    User findUserByName(String name);
    public void save(User user);
    public List<User> getUserList();
}
