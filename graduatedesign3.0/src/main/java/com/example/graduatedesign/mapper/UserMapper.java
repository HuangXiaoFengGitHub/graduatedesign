package com.example.graduatedesign.mapper;

import com.example.graduatedesign.Model.JacksonUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from users where userId=#{userId}")
    JacksonUser findUserByUserid(@Param("userId") String userId);
    @Insert("insert into users (userId,pwd) values(#{userId},#{pwd})")
    boolean insertUsers(@Param("userId") String userId, @Param("pwd") String pwd);
}
