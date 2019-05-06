package com.example.graduatedesign.controller.wechat;
import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.Model.pojo.ResultBean;
import com.example.graduatedesign.dto.Result;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.*;
import com.example.graduatedesign.util.CodeUtil;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    ObjectMapper objectMapper;
    @Resource
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    TagsService tagsService;
    @Autowired
    MajorService majorService;
    @Autowired
    AcademyService academyService;

    @RequestMapping("/getcurrentuser")
    @ResponseBody
    public Map<String,Object> getCurrentUser(HttpServletRequest request)
    {
        log.info("get current user:");
        Map<String,Object> map=new HashMap<>();
        User user=(User) request.getSession().getAttribute("user");
        if(user!=null)
        {
            map.put("success",true);
            map.put("userId",user.getUserId());
        }
        else {
            map.put("success",false);
            map.put("errMsg","你尚未登录");
        }
        return map;
    }

    @RequestMapping("/registerInitInfo")
    @ResponseBody
    public Map<String,Object> registerInitInfo()
    {
        log.info("get the registerInitInfo:");
        Map<String,Object> map=new HashMap<>();
        List<Academy> academies=academyService.findAll();
        List<Major> majors=majorService.findAll();
        map.put("academyList",academies);
        map.put("majorList",majors);
        map.put("success",true);
        return map;
    }

    @RequestMapping("/toRegister")
    public String toRegister()
    {
        return "wechat/userRegister";
    }
    /**
     * 注册
     * @param
     * @param request
     * @return
     */
    @RequestMapping("/userRegister")
    @ResponseBody
    public Map<String,Object> register(@RequestParam("thumbnail") MultipartFile profileImg, HttpServletRequest request,HttpServletResponse response)
    {
        //获取user对象，图片对象，和验证码对象
        Map<String,Object> map=new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            log.info("验证码错误");
            map.put("success",false);
            map.put("isWrong",true);
            map.put("errMsg","验证码错误");
            return map;
        }
        //获取专业和学院
        String userMajor=HttpServletRequestUtil.getString(request,"userMajor");
        log.info(userMajor);
        String userAcademy=HttpServletRequestUtil.getString(request,"userAcademy");
        log.info(userAcademy);
        String userStr= HttpServletRequestUtil.getString(request,"user");
        log.info(userStr);
        log.info("upload succesfully!");
//        MultipartHttpServletRequest multipartRequest=null;
//        CommonsMultipartFile profileImg=null;
//        CommonsMultipartResolver multipartResolver= new CommonsMultipartResolver(request.getSession().getServletContext());
//        if(multipartResolver.isMultipart(request))
//        {
//            multipartRequest=(MultipartHttpServletRequest) request;
//            profileImg = (CommonsMultipartFile) multipartRequest
//                    .getFile("thumbnail");
//        }
//        else
//        {
//            model.addAttribute("success", false);
//            model.addAttribute("errMsg", "上传图片不能为空");
//            return model;
//
//        }
        User user=null;
        try {
            if(userStr!=null&&userStr.trim()!="") {
                user = objectMapper.readValue(userStr, User.class);
                if(userService.checkEmail(user.getEmail()))
                {
                    map.put("success",false);
                    map.put("errMsg","不能使用一个邮箱重复注册");
                }
                if(userService.checkNiceame(user.getNickName()))
                {
                    map.put("success",false);
                    map.put("errMsg","昵称重复");
                }
                log.info("转换成功");
            }
            else {
                map.put("success",false);
                map.put("errMsg","传入了空的注册信息");
            }
        } catch (IOException e) {
            map.put("success",false);
            map.put("errMsg",e.toString());
         //   return "wechat/userRegisterResult";
            return map;
        }
        // 插入专业和学院信息；
        Major major=majorService.findMajorByName(userMajor);
        Academy academy=academyService.findAcademyByName(userAcademy);
        user.setMajor(major);
        user.setAcademy(academy);
        if(user!=null && user.getPassword()!=null  )
        {
            try {
                UserExecution le = userService.register(user,
                        profileImg);
                if (le.getState() == UserStateEnum.SUCCESS.getState()) {
                    map.put("success", true);
                } else {
                   map.put("success", false);
                   map.put("errMsg", le.getStateInfo());
                }
            } catch (RuntimeException e) {
                map.put("success", false);
                map.put("errMsg", e.toString());
        //        return "wechat/userRegisterResult";
                return map;
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","请输入注册信息");
        }
        //return "wechat/userRegisterResult";
        return map;
    }
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "wechat/userLogin";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request) throws IOException {
        Map<String,Object> map=new HashMap<>();
        //输入三次错误的密码就需要验证
        boolean needVerify=HttpServletRequestUtil.getBoolean(request,"needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            log.info("验证码错误");
            map.put("success",false);
            map.put("errMsg","验证码错误");
            return map;
        }
        String email=HttpServletRequestUtil.getString(request,"email");
        String password=HttpServletRequestUtil.getString(request,"password");
        log.info(email);
        log.info(password);
        UserExecution userExecution=userService.checkLogin(email,password);
        if (userExecution.getState()==1) {
            //登录成功 重定向到首页
            request.getSession().setAttribute("user",userExecution.getUser());
            //保存session
            User user=(User)request.getSession().getAttribute("user");
            log.info(user.toString());
            map.put("success",true);
        } else {
            log.info("登录失败");
           map.put("success",false);
           map.put("errMsg",userExecution.getStateInfo());
            //     throw new LoginException("登录失败！ 邮箱或者密码错误");
        }
        return map;
    }
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Map<String,Object> updatePwd(HttpServletRequest request)
    {
        log.info("修改密码：");
        Map<String,Object> map=new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            log.info("验证码错误");
            map.put("success",false);
            map.put("errMsg","验证码错误");
            return map;
        }
        String password=HttpServletRequestUtil.getString(request,"password");
        String newPassword=HttpServletRequestUtil.getString(request,"newPassword");
        String repeatPassword=HttpServletRequestUtil.getString(request,"repeatPassword");
        log.info("password:"+password);
        if(!repeatPassword.equals(newPassword))
        {
            map.put("success",false);
            map.put("errMsg","前后密码不一致！请确认密码！");
            return map;
        }
        if(password.equals(newPassword))
        {
            map.put("success",false);
            map.put("errMsg","新旧密码一致！");
            return map;
        }
        User user=(User)request.getSession().getAttribute("user");
        if(user==null)
        {
            map.put("success",false);
            map.put("errMsg","你未登录！！");
            return map;
        }
        UserExecution userExecution=userService.updatePassword(user.getUserId(),password);
        if (userExecution.getState()==1) {
            //登录成功 重定向到首页
            map.put("success",true);
        } else {
            map.put("success",false);
            map.put("errMsg",userExecution.getStateInfo());
            //     throw new LoginException("登录失败！ 邮箱或者密码错误");
        }
        return map;
    }



    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String,Object> getUserInfoByUserId(@RequestParam(value = "userId",defaultValue = "0") long userId)
    {
        //获取user对象，图片对象，和验证码对象
        Map<String,Object> map=new HashMap<>();
        log.info("userId:"+userId);
        User user=null;
        if(userId>0)
            user=userService.findUserByUserId(userId);
       // log.info(user.toString());
        if(user!=null)
        {
            map.put("user",user);
            map.put("success",true);
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","请输入userid");
        }
        return map;
    }
    @RequestMapping("/toUserEdit")
    public String toUserEdit()
    {
        return "user/userEdit";
    }
    @RequestMapping("/userEdit")
    @ResponseBody
    public Map<String,Object> userEdit(Model model, HttpServletRequest request,@RequestParam(value = "userId",defaultValue = "0")long userId)
    {
        log.info("userId:"+userId);
        Map<String,Object> map=new HashMap<>();
        MultipartFile profileImg =( MultipartFile)request.getAttribute("thumbnail");
        //1,首先检查验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            log.info("验证码错误");
            map.put("success",false);
            map.put("isWrong",true);
            map.put("errMsg","验证码错误");
            return map;
        }
        //获取专业和学院
        String userMajor=HttpServletRequestUtil.getString(request,"userMajor");
        log.info(userMajor);
        String userAcademy=HttpServletRequestUtil.getString(request,"userAcademy");
        log.info(userAcademy);
//        // 检查图片
//        if(profileImg.isEmpty())
//        {
//            map.put("success",false);
//            map.put("errMsg","验证码错误");
//            return map;
//        }
        //2，获取原来的用户
        User newUser=null;
        String userStr=HttpServletRequestUtil.getString(request,"user");
        try {
            if(userStr!=null&&userStr.trim()!="")
            newUser=objectMapper.readValue(userStr,User.class);
            else {
                map.put("success",false);
                map.put("errMsg","传入了空的修改信息");
                return map;
            }
        } catch (IOException e) {
            map.put("success",false);
            map.put("errMsg",e.toString());
            return map;
        }
        if(newUser!=null)
        {
            try {
                //从session中获取原来的user
//                User currentUser = (User) request.getSession().getAttribute("user");
//                newUser.setUserId(currentUser.getUserId());
                newUser.setUserId(userId);
                Major major=majorService.findMajorByName(userMajor);
                Academy academy=academyService.findAcademyByName(userAcademy);
                newUser.setMajor(major);
                newUser.setAcademy(academy);
                //3，修改用户信息
                log.info(newUser.toString());
                UserExecution le = userService.modifyUser(newUser, profileImg);
                if (le.getStateInfo() == UserStateEnum.SUCCESS.getStateInfo()) {
                    map.put("success", true);
                } else {
                    map.put("success", false);
                    map.put("errMsg", le.getStateInfo());
                }
            }
            catch (Exception e)
            {
                map.put("success",false);
                map.put("errMsg",e.toString());
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","修改信息为空");
            return map;
        }
        return map;
    }
    @RequestMapping("/list")
    //传入model对象，让前端可以使用User对象
    public String list(Model model) {
        List<User> users=userService.getUserList();
        model.addAttribute("hxf", users);
        return "wechat/list";
    }



    /**
     * 登出
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Map<String,Object> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> map=new HashMap<>();
        //注销session
        request.getSession().setAttribute("user",null);
        map.put("success",true);
        //response.sendRedirect("/wechat/toLogin");
        return map;
    }


    @RequestMapping("/registerResult")
    public String loginResult()
    {
        return "wechat/userRegisterResult";
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
        } else {
            return true;
        }
    }

}
