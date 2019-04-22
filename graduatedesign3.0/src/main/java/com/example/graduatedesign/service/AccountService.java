package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dao.AccountRepostory;
import com.example.graduatedesign.service.serviceImp.AccountServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService implements AccountServiceImp {
    @Autowired
    AccountRepostory accountRepostory;
    public Account findAccountByUser(User user)
    {
        return accountRepostory.findByUser(user);
    }
}
