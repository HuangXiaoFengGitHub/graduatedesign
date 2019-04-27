package com.example.graduatedesign.controller.frontent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/frontend")
public class FrontendController {
    @RequestMapping("/index")
    public String index()
    {
        return "frontend/index";
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
        return "frontend/index";
    }
}
