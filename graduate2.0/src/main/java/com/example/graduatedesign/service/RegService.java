package com.example.test1.demo.service;

import com.example.test1.demo.Model.JacksonUser;
import com.example.test1.demo.mapper.UserMapper;
import com.example.test1.demo.service.serviceImp.IRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegService  implements IRegService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean regUser(String userId,String pwd)
    {
        Boolean flag;
        try{
            flag=userMapper.insertUsers(userId,pwd);
        }
        catch (Exception e)
        {
            return false;
        }
        return flag;
    }
    @Override
    public JacksonUser findUser(String userId)
    {
        return userMapper.findUserByUserid(userId);
    }
}
