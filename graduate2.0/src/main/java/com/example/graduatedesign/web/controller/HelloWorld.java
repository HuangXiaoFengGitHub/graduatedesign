package com.example.test1.demo.web.controller;

import com.example.test1.demo.Model.JacksonUser;
import com.example.test1.demo.service.serviceImp.IRegService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "helloworld", method = { RequestMethod.GET,
        RequestMethod.POST })
public class HelloWorld {
    @Autowired
    private IRegService regService;
    private ObjectMapper jsonMapper;
//    private XmlMapper xmlMapper;
//
//    public HelloWorld(@Autowired @Qualifier("json") ObjectMapper jsonMapper, @Autowired @Qualifier("xml") XmlMapper xmlMapper) {
//        this.jsonMapper = jsonMapper;
//        this.xmlMapper = xmlMapper;
//    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    private String register() {
    return "register";
} //返回注册页面
    @RequestMapping("/webJarTest")
    private String webJarTest()
    {
        return "test/webjartest";
    }
    @RequestMapping("/test")
    @ResponseBody
    String home() {
        return regService.findUser("hxf").toString();
    }

    @RequestMapping(value = "/json", produces = "application/json")
    @ResponseBody
    public JacksonUser json() {
        return JacksonUser.builder().id("1").pwd("1996").userId("2019").build();
    }
    @RequestMapping("/reg")
    @ResponseBody
    Boolean reg(@RequestParam("loginPwd") String loginNum, @RequestParam("userId") String userId ){
        String pwd = creatMD5(loginNum);
        System.out.println(userId+":"+loginNum);
        return regService.regUser(userId,pwd);
    }
    private String creatMD5(String loginNum){
        // 生成一个MD5加密计算摘要
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(loginNum.getBytes());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, md.digest()).toString(16);
    }

}
