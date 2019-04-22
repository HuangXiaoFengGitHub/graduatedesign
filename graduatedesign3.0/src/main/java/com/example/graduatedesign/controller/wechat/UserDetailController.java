package com.example.graduatedesign.controller.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/user/detail")
public class UserDetailController {
    @RequestMapping("/myActivity")
    public String myActivity()
    {
        return "wechat/myActivity";
    }
    @RequestMapping("/likeActivityList")
    public String activityList()
    {
        return "wechat/likeActivitylist";
    }
    @RequestMapping("/likeOrganizationList")
    public String organizationList()
    {
        return "wechat/likeOrganizationlist";
    }
    @RequestMapping("/signUpActivityList")
    public String signUpActivityList()
    {
        return "wechat/signUpActivitylist";
    }
    @RequestMapping("/myTags")
    public String findMyTags()
    {
        return "wechat/userTags";
    }
}
