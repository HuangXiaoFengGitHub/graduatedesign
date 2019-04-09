package com.example.test1.demo.service.serviceImp;

import com.example.test1.demo.Model.JacksonUser;

public interface IRegService {
    boolean regUser(String userId,String pwd);
    JacksonUser findUser(String userId);
}
