package com.example.graduatedesign.service;


import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.serviceImp.UserServiceImp;
import com.example.graduatedesign.util.FileUtil;
import com.example.graduatedesign.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.security.provider.MD5;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public UserExecution register(User user, CommonsMultipartFile profileImg)
    {
        //将图片存入文件
        //获取存入路径
        //将User对象存入数据库
        if (user == null || user.getUserName() == null) {
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
            if (effectedNum <= 0) {
                throw new RuntimeException("帐号创建失败");
            } else {
                return new UserExecution(UserStateEnum.SUCCESS,user);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertLocalAuth error: "
                    + e.getMessage());
        }
    }
    private void addProfileImg(User user,CommonsMultipartFile profileImg) {
        String dest = FileUtil.getPersonInfoImagePath();//"/upload/images/item/userinfo/";
        String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);//创建文件，获取文件名
        user.setProfile(profileImgAddr);
    }
}
