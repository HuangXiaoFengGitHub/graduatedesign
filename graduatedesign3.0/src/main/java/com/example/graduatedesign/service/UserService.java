package com.example.graduatedesign.service;


import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.OrganizationRepository;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.ActivityState;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.serviceImp.UserServiceImp;
import com.example.graduatedesign.util.FileUtil;
import com.example.graduatedesign.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.security.provider.MD5;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService implements UserServiceImp {
    @Autowired
    UserRepostory userRepostory;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    public User findUserByName(String name)
 {
     return userRepostory.findByUserName(name);
 }
    public long save(User user)
    {
      return  userRepostory.saveAndFlush(user).getUserId();
    }
    @Override
    public List<User> getUserList() {
        return userRepostory.findAll();
    }
    @Override
    @Transactional
    public UserExecution register(User user, MultipartFile profileImg)
    {
        //将图片存入文件
        //获取存入路径
        //将User对象存入数据库
        log.info("begin register:");
        if (user == null || user.getPassword() == null) {
            return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
        }
        try {
            user.setCreateTime(Calendar.getInstance());
         //   user.setPassword(MD5.getMd5(user.getPassword()));
                if (profileImg != null) {
                    user.setIsBan(1);
                    try {
                        addProfileImg(user, profileImg);
                    } catch (Exception e) {
                        throw new RuntimeException("addUserProfileImg error: "
                                + e.getMessage());
                    }
                }
            long effectedNum = this.save(user);
            log.info("effectedNum:"+effectedNum+"");
            if (effectedNum <= 0) {
                throw new RuntimeException("帐号创建失败");
            } else {
                return new UserExecution(UserStateEnum.SUCCESS,user);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertUser error: "
                    + e.getMessage());
        }
    }
    private void addProfileImg(User user,MultipartFile profileImg) {
        log.info("begin add profileImg:");
        String dest = FileUtil.getPersonInfoImagePath();//"/upload/images/item/userinfo/";
        String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);//创建文件，获取文件名
        user.setProfile(profileImgAddr);
        log.info("profile dir:"+user.getProfile());
    }
    public User checkLogin(String username, String password) {
        return userRepostory.findByUserNameAndPassword(username, password);
    }
    @Transactional
    public UserExecution modifyUser(User user,MultipartFile profileImg)
    {
        User tempUser = userRepostory.findByUserId(user.getUserId());//修改用户，先取出原来的用户
        log.info("begin modify:");
        //非空判断
        if(user==null)
        {
            log.info("user is null!");
            return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
        }
        //判断是否需要修改图片，先把原来的图片删除，再插入新的图片
        //
        try {
           if (profileImg!=null && !profileImg.isEmpty()) {
               String relativePath = tempUser.getProfile();
               log.info(relativePath);
               FileUtil.deleteFile(relativePath);
               try {
                   addProfileImg(tempUser, profileImg);
               } catch (Exception e) {
                   throw new RuntimeException("addShopImg error: "
                           + e.getMessage());
               }
           }
           //2,更新用户信息
           tempUser.setUpdateTime(Calendar.getInstance());
           tempUser.setUserName(user.getUserName());
           tempUser.setNickName(user.getNickName());
           tempUser.setEmail(user.getEmail());
           tempUser.setStudentNumber(user.getStudentNumber());
           tempUser.setPhone(user.getPhone());
           tempUser.setPassword(user.getPassword());
           tempUser.setMajor(user.getMajor());
           tempUser.setGrade(user.getGrade());
           tempUser.setGender(user.getGender());
           tempUser.setAcademy(user.getAcademy());
           tempUser.setBirthday(user.getBirthday());
           tempUser.setUserDesc(user.getUserDesc());
           tempUser.setIsStudent(user.getIsStudent());
           log.info("tempUser:"+tempUser.toString());
           //3.插入数据库，更新信息
           long effectNum = this.save(tempUser);
           log.info("effectNum:" + effectNum);
           if (effectNum <= 0) {
               throw new RuntimeException("修改失败");
           } else {
               return new UserExecution(UserStateEnum.SUCCESS, user);
           }
       }
        catch (Exception e)
        {
            throw new RuntimeException("修改用户失败："+e.getMessage());
        }
    }
    public  User findUserByUserId(long userId)
    {
        return userRepostory.findByUserId(userId);
    }
    public Set<Activity> findMyLikeActivity(User user)
    {
        User user2=userRepostory.findByUserId(user.getUserId());
        if(user2!=null)
            return user2.getLikeActivities();
        else
            return null;
    }
    public Set<Tags> findMyTags(User user)
    {
        User user2=userRepostory.findByUserId(user.getUserId());
        log.info("user2:"+user2.toString());
        if(user2!=null)
        {
            return user2.getTags();
        }
        else
            return null;
    }
    public void addMyTags(User user,List<Tags> tags)
    {
        User user1=userRepostory.findByUserId(user.getUserId());
        user1.getTags().addAll(tags);
        userRepostory.save(user1);
    }
    public  ActivityExecution addMyLikeActivity(User user, long activityId,boolean isAdd) {
        Activity activity=activityRepository.findByActivityId(activityId);
        User user1=userRepostory.findByUserId(user.getUserId());
        if(isAdd)
            user1.getLikeActivities().add(activity);
        else
            user1.getLikeActivities().remove(activity);
        if(userRepostory.saveAndFlush(user1)!=null)
        {
            return new ActivityExecution(ActivityState.SUCCESS,activity);
        }
        else
        {
            return new ActivityExecution(ActivityState.FAILURE,activity);
        }
    }
    public ActivityExecution addMySignUpActivity(User user,long activityId) {
        User user1=userRepostory.findByUserId(user.getUserId());
        Activity activity=activityRepository.findByActivityId(activityId);
        user1.getLikeActivities().add(activity);
        if(userRepostory.saveAndFlush(user1)!=null)
        {
            return new ActivityExecution(ActivityState.SUCCESS,activity);
        }
        else
        {
            return new ActivityExecution(ActivityState.FAILURE,activity);
        }
    }
    public User addMyLikeOrganization(User user, long organizationId){
        User user1=userRepostory.findByUserId(user.getUserId());
        Organization organization=organizationRepository.findByOrganizationId(organizationId);
        user1.getLikeOrganizations().add(organization);
        return userRepostory.saveAndFlush(user1);
    }
}
