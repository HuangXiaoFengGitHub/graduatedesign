package com.example.graduatedesign.controller.manager;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/frontend")
public class ManagerFrontendController {
    @Autowired
    ActivityService activityService;

    @RequestMapping("/tocheck")
    public String toCheck()
    {
        return "manager/activityCheck";
    }
    @RequestMapping("/checking")
    public String toChecking(Model model, @RequestParam(defaultValue = "0")long activityId)
    {
        Activity activity=activityService.findActivityById(activityId);
        model.addAttribute("activity",activity);
        return "manager/activityChecking";
    }


}
