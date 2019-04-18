package com.example.graduatedesign.service;


import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.serviceImp.UserServiceImp;
import com.example.graduatedesign.util.FileUtil;
import com.example.graduatedesign.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.security.provider.MD5;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class UserService implements UserServiceImp {
    @Autowired
    UserRepostory userRepostory;
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
        log.info("begin modify:");
        //非空判断
        if(user==null)
        {
            return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
        }
        //判断是否需要修改图片，先把原来的图片删除，再插入新的图片
        //
        try {
           if (!profileImg.isEmpty()) {
               User tempUser = userRepostory.findByUserId(user.getUserId());
               String relativePath = tempUser.getProfile();
               log.info(relativePath);
               FileUtil.deleteFile(relativePath);
               try {
                   addProfileImg(user, profileImg);
               } catch (Exception e) {
                   throw new RuntimeException("addShopImg error: "
                           + e.getMessage());
               }
           }
           //2,更新用户信息
           user.setUpdateTime(Calendar.getInstance());
           long effectNum = this.save(user);
           log.info("effectNum:" + effectNum);
           if (effectNum <= 0) {
               throw new RuntimeException("帐号创建失败");
           } else {
               return new UserExecution(UserStateEnum.SUCCESS, user);
           }
       }
        catch (Exception e)
        {
            throw new RuntimeException("修改用户失败："+e.getMessage());
        }
    }
}
