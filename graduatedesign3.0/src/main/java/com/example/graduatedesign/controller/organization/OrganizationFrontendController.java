package com.example.graduatedesign.controller.organization;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Place;
import com.example.graduatedesign.service.ActivityCategoryService;
import com.example.graduatedesign.service.ActivityService;
import com.example.graduatedesign.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/organization/frontend")
public class OrganizationFrontendController {
    @Autowired
    ActivityService activityService;
    @Autowired
    PlaceService placeService;
    @Autowired
    ActivityCategoryService activityCategoryService;
    @RequestMapping("/getApplyHistory")
    public String getApplyHistory(Model model, @RequestParam(defaultValue = "0") long activityId)
    {
        //  Activity activity=activityService.findActivityById(activityId);
        //     model.addAttribute("activity",activity);
        return "organization/applyHistory";
    }
    @RequestMapping("/getActivityDetail")
    public String getActivityDetail(Model model, @RequestParam(defaultValue = "0")long activityId)
    {
        Activity activity=activityService.findActivityById(activityId);
        model.addAttribute("activity",activity);
        return "organization/activityDetail";
    }
    @RequestMapping("/tocompose")
    public String toCompose(Model model,@RequestParam(defaultValue = "0")long activityId)
    {
        Activity activity=activityService.findActivityById(activityId);
        //获取活动类别信息
        List<ActivityCategory> activityCategories=activityCategoryService.findAll();
        List<Place> places=placeService.findAll();
        model.addAttribute("categorys",activityCategories);
        model.addAttribute("places",places);
        model.addAttribute("activity",activity);
        return "organization/activityCompose";
    }
    //申请活动
    @RequestMapping("/toApply")
    public String toApply(Model model)
    {
        //获取活动类别信息
        List<ActivityCategory> activityCategories=activityCategoryService.findAll();
        List<Place> places=placeService.findAll();
        model.addAttribute("categorys",activityCategories);
        model.addAttribute("places",places);
        return "organization/activityApply";
    }


}
