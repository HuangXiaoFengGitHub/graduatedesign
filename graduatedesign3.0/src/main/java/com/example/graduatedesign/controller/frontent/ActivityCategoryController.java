package com.example.graduatedesign.controller.frontent;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.service.ActivityCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/frontend/activityCategory")

public class ActivityCategoryController {
    @Autowired
    ActivityCategoryService activityCategoryService;
    /**
     * 点击全部活动后，返回所有活动类别信息
     * @return
     */
    @RequestMapping("/getAllActivityCategory")
    @ResponseBody
    public Map<String,Object> getall()
    {
        Map<String,Object> map=new HashMap<>();
        List<ActivityCategory> activityCategoryList=null;
        try
        {
            activityCategoryList=activityCategoryService.findAll();
            map.put("activityCategoryList",activityCategoryList);
            map.put("success",true);
        }
        catch (Exception e)
        {
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
        return map;
    }
}
