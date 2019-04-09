package com.example.test1.demo.dao;

import com.example.test1.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostory extends JpaRepository<User,Long> {
    User findByUserId(long id);
    User findByUserName(String name);
}
