package com.example.graduatedesign.service.serviceImp;


import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.UserExecution;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface UserServiceImp {
    public User findUserByName(String name);
    public long save(User user);
    public List<User> getUserList();
    public UserExecution register(User user, CommonsMultipartFile profileImg);
//    public void addProfileImg(User user, CommonsMultipartFile profileImg);
}
