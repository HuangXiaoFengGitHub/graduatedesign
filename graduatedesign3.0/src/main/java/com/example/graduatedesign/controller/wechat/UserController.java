package com.example.graduatedesign.controller.wechat;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.UserService;
import com.example.graduatedesign.util.CodeUtil;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    @ResponseBody
    public Model register(Model model,HttpServletRequest request)
    {
        if (!CodeUtil.checkVerifyCode(request)) {
            model.addAttribute("success", false);
            model.addAttribute("errMsg", "输入了错误的验证码");
            return model;
        }
        //获取user对象，图片对象，和验证码对象
        String userStr= HttpServletRequestUtil.getString(request,"user");
        MultipartHttpServletRequest multipartRequest=null;
        CommonsMultipartFile profileImg=null;
        CommonsMultipartResolver multipartResolver= new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request))
        {
            multipartRequest=(MultipartHttpServletRequest) request;
            profileImg = (CommonsMultipartFile) multipartRequest
                    .getFile("thumbnail");
        }
        else
        {
            model.addAttribute("success", false);
            model.addAttribute("errMsg", "上传图片不能为空");
            return model;

        }
        User user=null;
        try {
            user=objectMapper.readValue(userStr,User.class);
        } catch (IOException e) {
            model.addAttribute("success",false);
            model.addAttribute("errMsg",e.toString());
            return model;
        }
        if(user!=null && user.getUserName()!=null  )
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
                return model;
            }
        }
        else
        {
            model.addAttribute("success",false);
            model.addAttribute("errMsg","请输入注册信息");
        }


        return model;
    }
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "wechat/userLogin";
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/list";
    }

    @RequestMapping("/list")
    //传入model对象，让前端可以使用User对象
    public String list(Model model) {
        List<User> users=userService.getUserList();
        model.addAttribute("hxf", users);
        return "wechat/list";
    }


}
