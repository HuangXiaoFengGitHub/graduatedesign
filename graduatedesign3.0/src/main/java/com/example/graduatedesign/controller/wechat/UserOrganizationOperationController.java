package com.example.graduatedesign.controller.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user/organizationOperate")
public class UserOrganizationOperationController {
    @RequestMapping(value = "/addLikeOrganization")
    public Map<String,Object> addLikeOrganization(long organizationId)
    {
        Map<String,Object> map=new HashMap<>();
        return map;
    }
}
