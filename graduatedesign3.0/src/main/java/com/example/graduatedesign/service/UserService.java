package com.example.graduatedesign.service;


import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dao.ActivityCommentRepository;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.OrganizationRepository;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.ActivityState;
import com.example.graduatedesign.enums.OrganizationState;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.serviceImp.UserServiceImp;
import com.example.graduatedesign.util.FileUtil;
import com.example.graduatedesign.util.ImageUtil;
import com.example.graduatedesign.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Autowired
    ActivityCommentRepository activityCommentRepository;
    public User findUserByName(String name)
 {
     return userRepostory.findByUserName(name);
 }
    public User findManagerByName(String name)
    {
        return userRepostory.findByUserNameAndIsManager(name,1);
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
        log.info("begin register:");
        if (user == null || user.getPassword() == null) {
            return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
        }
        try {
            user.setCreateTime(Calendar.getInstance());
         //   user.setPassword(MD5Util.getMd5(user.getPassword()));
                if (profileImg != null) {
                    user.setIsBan(1);
                    try {
                        addProfileImg(user, profileImg);
                    } catch (Exception e) {
                        throw new RuntimeException("addUserProfileImg error: "
                                + e.getMessage());
                    }
                }
                //MD5加密
                String password= MD5Util.getMd5(user.getPassword());
                user.setPassword(password);
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
        if(isAdd)
            log.info("关注活动");
        else
            log.info("取消关注活动");
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
    public boolean addLikeComment(User user,long commentId,boolean islike)
    {
        if(islike)
            log.info("关注活动");
        else
            log.info("取消关注活动");
        ActivityComment activityComment=activityCommentRepository.findByCommentId(commentId);
        User user1=userRepostory.findByUserId(user.getUserId());
        if(islike)
        {
            if(user1.getLikeComments().contains(activityComment))
                return false;
            user1.getLikeComments().add(activityComment);
        }
        else
            user1.getLikeComments().remove(activityComment);
        if(userRepostory.saveAndFlush(user1)!=null)
        {
            activityComment.setCommentLikeCount(activityComment.getLikeUsers().size());
            activityCommentRepository.save(activityComment);
            return true;
        }
        else
        {
            return false;
        }
    }
    public ActivityExecution addMySignUpActivity(User user,long activityId,boolean isSignUp) {
        User user1=userRepostory.findByUserId(user.getUserId());
        Activity activity=activityRepository.findByActivityId(activityId);
        if(isSignUp)
            user1.getSignUpActivities().add(activity);
        else
            user1.getSignUpActivities().remove(activity);
        if(userRepostory.saveAndFlush(user1)!=null)
        {
            return new ActivityExecution(ActivityState.SUCCESS,activity);
        }
        else
        {
            return new ActivityExecution(ActivityState.FAILURE,activity);
        }
    }
    public OrganizationExecution addMyLikeOrganization(User user, long organizationId, boolean isAdd){
        if(isAdd)
            log.info("关注组织");
        else
            log.info("取消关注组织");
        Organization organization=organizationRepository.findByOrganizationId(organizationId);
        User user1=userRepostory.findByUserId(user.getUserId());
        if(isAdd)
            user1.getLikeOrganizations().add(organization);
        else
            user1.getLikeOrganizations().remove(organization);
        if(userRepostory.saveAndFlush(user1)!=null)
        {
            return new OrganizationExecution(OrganizationState.SUCCESS,organization);
        }
        else
        {
            return new OrganizationExecution(OrganizationState.FAILURE,organization);
        }
    }
    public boolean isLike(long activityId,User user)
    {
        log.info("判断是否关注");
        if(user!=null)
        {
            User user1=userRepostory.findByUserId(user.getUserId());
            log.info(user1.getLikeActivities().toString());
            if(user1!=null)
            {
                Activity activity=activityRepository.findByActivityId(activityId);
                return user1.getLikeActivities().contains(activity);
            }
            else
                return false;
        }
        else {
            return false;
        }

    }
    public boolean isSignUp(long activityId,User user)
    {
        log.info("判断是否报名成功：");
        if(user!=null) {
            User user1 = userRepostory.findByUserId(user.getUserId());
            log.info(user1.getSignUpActivities().toString());
            Activity activity = activityRepository.findByActivityId(activityId);
            if(user1!=null)
                return user1.getSignUpActivities().contains(activity);
            else
                return false;
        }
        else
            {
             return false;
        }
    }

    /**
     * 检查登录
     * @param email
     * @param password
     * @return
     */
    public UserExecution checkLogin(String email,String password)
    {
        if(StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password))
        {
            String password1=MD5Util.getMd5(password);
            User user=userRepostory.findByEmailAndPassword(email,password1);
            if(user!=null)
                return new UserExecution(UserStateEnum.SUCCESS,user);
            else
                return new UserExecution(UserStateEnum.LOGINFAIL);
        }
        else
        {
            return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
        }
    }

    /**
     * 修改密码
     * @param userId
     * @param password
     * @return
     */
    public UserExecution updatePassword(long userId,String password)
    {
        if(StringUtils.isNotBlank(password))
        {
            User user=userRepostory.findByUserId(userId);
            if(user!=null)
            {
                if(user.getPassword().equals(MD5Util.getMd5(password)))
                {
                    return new UserExecution(UserStateEnum.REPEAT);
                }
                user.setPassword(MD5Util.getMd5(password));
                User newUser=userRepostory.saveAndFlush(user);
                return new UserExecution(UserStateEnum.SUCCESS,user);
            }
            else {
                return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
            }
        }
        else {
            return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
        }
    }

    /**
     * 验证用户名是否唯一
     * @param
     * @return
     */
    public boolean checkEmail(String email){
        User users = userRepostory.findByEmail(email);
        return users==null;
    }
    public boolean checkNiceame(String nickname){
        User users = userRepostory.findByNickName(nickname);
        return users==null;
    }



}
