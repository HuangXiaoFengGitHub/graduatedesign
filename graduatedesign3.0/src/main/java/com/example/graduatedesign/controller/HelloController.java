package com.example.graduatedesign.controller;


import com.example.graduatedesign.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HelloController {
    @Autowired
    OrganizationService organizationService;
    @RequestMapping("/hello")
    public String hello2(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name2", name);
        return "hello";
    }
    @RequestMapping("/hello2")
    public String hello(Model model,@RequestParam(value="name", required=false, defaultValue="World") String name)
    {
        model.addAttribute("name3",name);
        return "organization/organizationRegister2";
    }

    @RequestMapping("/layui")
    public String layui()
    {
        return "test/layuiTest";
    }
}
