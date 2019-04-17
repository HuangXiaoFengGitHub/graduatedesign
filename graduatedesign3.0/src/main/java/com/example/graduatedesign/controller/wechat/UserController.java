package com.example.graduatedesign.controller.wechat;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.Model.pojo.ResultBean;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.UserService;
import com.example.graduatedesign.util.CodeUtil;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    ObjectMapper objectMapper;
    @Resource
    UserService userService;

    @RequestMapping("/toRegister")
    public String toRegister()
    {
        return "wechat/userRegister";
    }

    /**
     * 注册
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/register")
    public String register(@RequestParam("thumbnail") MultipartFile profileImg, Model model, HttpServletRequest request)
    {
//        if (!CodeUtil.checkVerifyCode(request)) {
//            model.addAttribute("success", false);
//            model.addAttribute("errMsg", "输入了错误的验证码");
//            return model;
//        }
        //获取user对象，图片对象，和验证码对象
        String userStr= HttpServletRequestUtil.getString(request,"user");
        log.info(userStr);
        if(profileImg.isEmpty())
        {
            model.addAttribute("message", "Please select a file to upload");
            return "wechat/userRegisterResult";
        }
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
            user=objectMapper.readValue(userStr,User.class);
            log.info("translate successfullly");
        } catch (IOException e) {
            model.addAttribute("success",false);
            model.addAttribute("errMsg",e.toString());
            return "wechat/userRegisterResult";
        }
        if(user!=null && user.getPassword()!=null  )
        {
            try {
                UserExecution le = userService.register(user,
                        profileImg);
                if (le.getState() == UserStateEnum.SUCCESS.getState()) {
                    model.addAttribute("success", true);
                } else {
                    model.addAttribute("success", false);
                    model.addAttribute("errMsg", le.getStateInfo());
                }
            } catch (RuntimeException e) {
                model.addAttribute("success", false);
                model.addAttribute("errMsg", e.toString());
                return "wechat/userRegisterResult";
            }
        }
        else
        {
            model.addAttribute("success",false);
            model.addAttribute("errMsg","请输入注册信息");
        }
        return "wechat/userRegisterResult";
    }
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "wechat/userLogin";
    }

    @RequestMapping("/login")
    public void login(String email,
                      String password,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        //
        User user =userService.checkLogin(email, password);
        if (user != null) {
            //登录成功 重定向到首页
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/user/index");
        } else {
            log.info("登录失败");
            request.getSession().setAttribute("error","登录失败，邮箱或者密码错误");
            response.sendRedirect("/organization/error");
            //     throw new LoginException("登录失败！ 邮箱或者密码错误");
        }
    }

    @RequestMapping("/index")
    public String index() {
        return "wechat/userIndex";
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
    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("/wechat/userIndex.html");
    }


    /**
     * 验证用户名是否唯一
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkUsername.do")
    public ResultBean<Boolean> checkUsername(String username){
      User users = userService.findUserByName(username);
        if (users==null){
            return new ResultBean<>(true);
        }
        return new ResultBean<>(false);
    }

}
