package com.example.graduatedesign.controller.manager;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ManagerOrganization;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.Result;
import com.example.graduatedesign.service.ActivityService;
import com.example.graduatedesign.service.ManagerOrganizationService;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/manager/activity")
public class ActivityCheckController {
    @Autowired
    ManagerOrganizationService managerOrganizationService;
    @Autowired
    ActivityService activityService;
    @RequestMapping("/getCheckActivity")
    @ResponseBody
    public Map<String,Object> getCheckActivity(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<>();
        log.info("获取审核组织：");
        User user=(User)request.getSession().getAttribute("manager");
        if(user!=null) {
            log.info(user.getUserName());
            ActivityExecution activityExecution = managerOrganizationService.findMyActivity(user.getUserId(), 0L, 1, 1);
            if (activityExecution.getState() == 1) {
               SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log.info(activityExecution.getActivityList().size()+"");
                map.put("activityList", activityExecution.getActivityList());
                map.put("success",true);
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","你尚未登录");
        }
        return map;
    }
    @RequestMapping("/checking")
    public String checking(@RequestParam(defaultValue = "0") long activityId)
    {
        log.info("activityId:"+activityId);
        return "manager/checkingActivity";
    }
    @RequestMapping("/check")
    @ResponseBody
    public Map<String, Object> check(HttpServletRequest request, @RequestParam(defaultValue = "0")long activityId)
    {
        Map<String,Object> map=new HashMap<>();
        log.info("提交审核："+activityId);
        User user=(User)request.getSession().getAttribute("manager");

        String isPass= HttpServletRequestUtil.getString(request,"isPass");
        String checkComment=HttpServletRequestUtil.getString(request,"checkComment");
        log.info(isPass);
        log.info(checkComment);
        if(user!=null) {
            //TO DO 审核活动的逻辑
            Activity activity=activityService.findActivityById(activityId);
            if(activity!=null)
            {
                ActivityExecution activityExecution=activityService.checkActivity(activity,isPass,checkComment);
                if(activityExecution.getState()==1)
                {
                    map.put("success",true);
                }
                else {
                    map.put("success",false);
                    map.put("errMsg",activityExecution.getStateInfo());
                }
            }
            else {
                map.put("success",false);
                map.put("errMsg","传入空的活动信息");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","你尚未登录");
        }
        return map;
    }

    /**
     * 根据活动id返回活动
     * @param activityId
     * @return
     */
    @RequestMapping("/getActivityDetail")
    @ResponseBody
    public Result<Activity> getActivityDetail(@RequestParam(defaultValue = "0") long activityId)
    {
        Activity activity=activityService.findActivityById(activityId);
        if(activity!=null)
            return new Result<>(true,activity);
        else
            return new Result<>(false,1,"找不到活动");
    }
}
