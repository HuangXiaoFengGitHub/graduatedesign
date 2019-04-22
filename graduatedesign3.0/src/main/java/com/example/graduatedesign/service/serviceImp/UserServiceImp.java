package com.example.graduatedesign.service.serviceImp;


import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.UserExecution;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Set;

public interface UserServiceImp {
    public User findUserByName(String name);
    User findUserByUserId(long userId);
    public long save(User user);
    public List<User> getUserList();
    public UserExecution register(User user, MultipartFile profileImg);
//    public void addProfileImg(User user, CommonsMultipartFile profileImg);
    public UserExecution modifyUser(User user,MultipartFile profileImg);
    Set<Activity> findMyLikeActivity(User user);
    Set<Tags> findMyTags(User user);
    void addMyTags(User user,List<Tags> tags);
    ActivityExecution addMyLikeActivity(User user,long activityId);
    ActivityExecution addMySignUpActivity(User user,Activity activity);
    void addMyLikeOrganization(User user, Organization organization);

}
