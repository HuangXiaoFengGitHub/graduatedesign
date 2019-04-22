package com.example.graduatedesign.dao;


import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepostory extends JpaRepository<Account,Long> {
    Account findByUser(User user);
}
