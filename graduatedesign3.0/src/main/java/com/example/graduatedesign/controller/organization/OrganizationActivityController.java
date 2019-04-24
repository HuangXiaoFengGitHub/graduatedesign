package com.example.graduatedesign.controller.organization;

import com.example.graduatedesign.Model.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/organization/activityOperate")
public class OrganizationActivityController {
    //申请活动
    @RequestMapping("/toApply")
    public String toApply()
    {
        return "organization/activityApply";
    }
    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    public Map<String,Object> create(Activity activity, HttpServletRequest request, HttpServletResponse response)
    {
        //接收编辑器的内容，作为文章id
        Map<String,Object> map=new HashMap<>();
        log.info("创建活动:");
        try {
            response.sendRedirect("/organization/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("/editActivity")
    public void editActivity()
    {

    }
    //发布活动
    @RequestMapping("/toCompose")
    public String toCompose()
    {
        return "organization/activityCompose";
    }
    @RequestMapping("/compose")
    public void compose()
    {

    }
}
