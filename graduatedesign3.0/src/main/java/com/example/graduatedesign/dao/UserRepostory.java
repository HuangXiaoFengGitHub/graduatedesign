package com.example.graduatedesign.dao;


import com.example.graduatedesign.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostory extends JpaRepository<User,Long> {
    User findByUserId(long id);
    User findByUserName(String name);
}
