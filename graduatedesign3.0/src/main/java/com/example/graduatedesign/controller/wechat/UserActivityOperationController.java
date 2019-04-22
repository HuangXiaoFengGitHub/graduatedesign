package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.service.AccountService;
import com.example.graduatedesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/addLikeActivity")
    public Map<String,Object> addLikeActivity(long activityId)
    {
        Map<String,Object> map=new HashMap<>();
        return map;
    }
    @RequestMapping(value = "/signUp")
    public Map<String,Object> signUp(long activityId)
    {
        Map<String,Object> map=new HashMap<>();
        return map;
    }
    /**
     * 填充关注活动信息的方法
     * @param request
     * @return
     */
    @RequestMapping(value="/getMyLikeActivity",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getActivity(HttpServletRequest request)
    {
        log.info("开始获取列表：");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取登录的用户信息
        User user = User.builder().userId(1L).userName("黄晓峰").build();
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
        Set<Activity> list;
        try {
//            ShopExecution shopExecution = shopService
//                    .getByEmployeeId(employeeId); //查询该用户下面的店铺信息
            // list = userService.findMyLikeActivity(user);//获取关注列表
            list=new HashSet<>();
            if(list!=null) {
                log.info(list.toString());
                Activity activity = Activity.builder().activityName("勤工助学").status(4).activityId(2L).startTime(Calendar.getInstance()).build();
                list.add(activity);
                log.info(list.toString());
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
