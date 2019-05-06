package com.example.graduatedesign.controller.frontent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/frontend")
public class FrontendController {
    @RequestMapping("/toRegister")
    public String toRegister()
    {
        return "wechat/userRegister";
    }
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "frontend/userlogin";
    }
    @RequestMapping("/index")
    public String index()
    {
        return "frontend/index";
    }
    @RequestMapping("/updatepassword")
    public String updatePassword()
    {
        return "frontend/changepsw";
    }
    /**
     * 获取对应活动类别的活动列表
     * @param activityCategoryId
     * @return
     */
    @RequestMapping("/activityList")
    public String activityList(@RequestParam(defaultValue = "0") long activityCategoryId)
    {
        log.info("activityCategoryId:"+activityCategoryId);
        return "frontend/activitylist";
    }
    @RequestMapping("getallactivity")
    public String getAllActivity()
    {
        log.info("getAllActivity");
        return "frontend/activitylist";
    }
    @RequestMapping("/getActivityNotice")
    public String getActivityNotice(@RequestParam(defaultValue = "0") long activityId)
    {
        log.info("activityId:"+activityId);
        return "frontend/activitynotice";
    }
    /**
     * 转发活动详情界面
     * @return
     */
    @RequestMapping("/activityDetail")
    public String getActivityDetail(@RequestParam(defaultValue = "0") long activityId)
    {
        log.info("activityId:"+activityId);
        return "frontend/activitydetail";
    }
    @RequestMapping("/getorganizationlist")
    public String getOrganizationList()
    {
        log.info("获取活动列表");
        return "frontend/organizationlist";
    }
    @RequestMapping("/organizationDetail")
    public String getOrganizationDetail(@RequestParam(defaultValue = "0") long organizationId)
    {
        log.info("organizationId:"+organizationId+"");
        return "frontend/organizationdetail";
    }
    @RequestMapping("/activity/getComment")
    public String comment(@RequestParam(defaultValue = "0") long activityId)
    {
        log.info("用户评论"+"activityId:"+activityId+"");
        return "frontend/activitycomment";
    }
    @RequestMapping("/organization/comment")
    public String organizationComment(@RequestParam(defaultValue = "0") long organizationId)
    {
        log.info("组织评论:"+organizationId);
        return "frontend/organizationcomment";
    }
    @RequestMapping("/user/post/comment")
    public String toPost(@RequestParam(defaultValue = "0") long activityId)
    {
        return "frontend/topostcomment";
    }



}
