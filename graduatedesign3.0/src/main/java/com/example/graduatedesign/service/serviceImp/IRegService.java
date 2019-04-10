package com.example.graduatedesign.service.serviceImp;


import com.example.graduatedesign.Model.JacksonUser;

public interface IRegService {
    boolean regUser(String userId, String pwd);
    JacksonUser findUser(String userId);
}
