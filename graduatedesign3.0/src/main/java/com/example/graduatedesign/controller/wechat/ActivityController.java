package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.service.AccountService;
import com.example.graduatedesign.service.ActivityService;
import com.example.graduatedesign.service.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    UserService userService;
    @Autowired
    ActivityService activityService;
    @Autowired
    AccountService accountService;
    @RequestMapping("/getActivityDetail")
    public String getActivityDetail(@RequestParam(value = "activityId")long activityId)
    {
        log.info("activityId:"+activityId+"");
        return "wechat/activityDetail";
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
