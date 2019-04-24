package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.enums.ActivityState;
import com.example.graduatedesign.service.AccountService;
import com.example.graduatedesign.service.ActivityService;
import com.example.graduatedesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
@Slf4j
@Controller
@RequestMapping("/user/activityOperate")
public class UserActivityOperationController {
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    ActivityService activityService;
    @RequestMapping(value = "/addLikeActivity")
    @ResponseBody
    public Map<String,Object> addLikeActivity(@RequestParam(value = "activityId",defaultValue = "0") long activityId,HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<>();
        User currentUser=(User) request.getSession().getAttribute("user");
        if(activityId >0 )
        {
            ActivityExecution activityExecution =userService.addMyLikeActivity(currentUser,activityId,true);
            if(activityExecution.getState()==1 )
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","关注失败");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择组织");
        }
        return map;
    }
    @RequestMapping("/cancelLikeActivity")
    @ResponseBody
    public Map<String,Object> cancelLikeActivity(@RequestParam(value = "activityId",defaultValue = "0") long activityId,HttpServletRequest request)
    {
        log.info("取消报名2：");
        log.info(activityId+"");
        Map<String,Object> map=new HashMap<>();
        //User currentUser=(User) request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(1L);
        if(activityId >0 )
        {
            ActivityExecution activityExecution =userService.addMyLikeActivity(currentUser,activityId,false);
            if(activityExecution.getState()==1 )
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","取消关注失败");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择活动");
        }

        return map;
    }

    @RequestMapping(value = "/signUp")
    public Map<String,Object> signUp(@RequestParam(value = "activityId",defaultValue = "0") long activityId,HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<>();
        User currentUser=(User) request.getSession().getAttribute("user");
        if(activityId >0 )
        {
            ActivityExecution activityExecution =userService.addMySignUpActivity(currentUser,activityId);
            if(activityExecution.getState()==1 )
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","报名失败");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择组织");
        }
        return map;
    }
    /**
     * 填充关注活动信息的方法
     * @param request
     * @return
     */
    @RequestMapping(value="/getMyLikeActivity",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getLikeActivity(HttpServletRequest request)
    {
        log.info("开始获取列表2：");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取登录的用户信息
        User user = userService.findUserByUserId(1L);
        log.info(user.toString());
        //user=(User) request.getSession().getAttribute("user");
        long employeeId;
        if(user!=null)
            employeeId = user.getUserId();//获取用户id
        else
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","用户信息为空");
            return modelMap;
        }
        if (hasAccountBind(request, employeeId)) {
            modelMap.put("hasAccountBind", true);
        } else {
            modelMap.put("hasAccountBind", false);
        }
        Set<Activity> list=user.getLikeActivities();
        log.info(list.toString());
        try {
            if(list!=null) {
//              //  Activity activity = Activity.builder().activityName("勤工助学").status(4).activityId(2L).startTime(Calendar.getInstance()).build();
//                Activity activity=activityService.findActivityById(4L);
//                activity.setStatus(4);
//                log.info(activity.toString());
//                userService.addMyLikeActivity(user,activity.getActivityId(),true);
                //log.info(list.toString());
                modelMap.put("likeActivityList", list); //传入前台，这里对应将page对象传入前台
                modelMap.put("user", user);
                modelMap.put("success", true);
                // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
                request.getSession().setAttribute("likeActivityList", list);
            }
            else
            {
                modelMap.put("success",false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }
    /**
     * 判断是否有绑定账号
     * @param request
     * @param userId
     * @return
     */
    private boolean hasAccountBind(HttpServletRequest request, long userId) {
        if (request.getSession().getAttribute("bindAccount") == null) {
            User user=userService.findUserByUserId(userId);
            Account localAuth = accountService.findAccountByUser(user);
            if (localAuth != null && localAuth.getUser() != null) {
                request.getSession().setAttribute("bindAccount", localAuth);
                return true;
            } else {
                return false;
            }
        }
        else {
            return true;
        }
    }
}
