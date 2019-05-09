package com.example.graduatedesign.controller.manager;

import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.service.ManagerOrganizationService;
import com.example.graduatedesign.service.UserService;
import com.example.graduatedesign.util.CodeUtil;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerOrganizationService managerOrganizationService;
    @Autowired
    UserService userService;

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request)
    {
        User user=(User) request.getSession().getAttribute("manager");
        if(user!=null)
        {
            int checking=managerOrganizationService.findCheckingNumber(user.getUserId());
            int checked=managerOrganizationService.findCheckedActivity(user.getUserId());
            int organizationNumber=managerOrganizationService.organizationNum(user.getUserId());
            model.addAttribute("checking",checking);
            model.addAttribute("checked",checked);
            model.addAttribute("organizationNumber",organizationNumber);
        }
        else
        {
            model.addAttribute("checking",0);
            model.addAttribute("checked",0);
            model.addAttribute("organizationNumber",0);
        }
        return "manager/managerIndex";
    }
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "manager/login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request)  {
        Map<String,Object> map=new HashMap<>();
        //输入三次错误的密码就需要验证
//        boolean needVerify=HttpServletRequestUtil.getBoolean(request,"needVerify");
//        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
//            log.info("验证码错误");
//            map.put("success",false);
//            map.put("errMsg","验证码错误");
//            return map;
//        }
        log.info("开始登陆：");
        String email=HttpServletRequestUtil.getString(request,"email");
        String password=HttpServletRequestUtil.getString(request,"password");
        log.info(email);
        log.info(password);
        UserExecution userExecution=managerOrganizationService.checkLogin(email,password);
        if (userExecution.getState()==1) {
            //登录成功 重定向到首页
            log.info("登陆成功");
            map.put("success1",true);
            map.put("errMsg",userExecution.getStateInfo());
            request.getSession().setAttribute("manager",userExecution.getUser());
        } else {
            log.info("登录失败");
            map.put("success1",false);
            map.put("errMsg",userExecution.getStateInfo());
            //     throw new LoginException("登录失败！ 邮箱或者密码错误");
        }
        return map;
    }
    /**
     * 登出
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("manager");
        response.sendRedirect("/manager/toLogin");
    }
}
