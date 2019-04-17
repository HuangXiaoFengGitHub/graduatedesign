package com.example.graduatedesign.controller.organization;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.Model.pojo.ResultBean;
import com.example.graduatedesign.exception.LoginException;
import com.example.graduatedesign.service.ManagerOrganizationService;
import com.example.graduatedesign.service.OrganizationService;
import com.example.graduatedesign.service.UserService;
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
import java.util.Calendar;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;
    @Autowired
    ManagerOrganizationService managerOrganizationService;
    @Autowired
    UserService userService;
    @RequestMapping("/index")
    public String index()
    {
        return "organization/organizationIndex";
    }
    @RequestMapping("/error")
    private String error2()
    {
        return "common/error";
    }
    @RequestMapping("/toRegister")
    public String toRegister(Model model)
    {
        List<Organization> topOrganizations=organizationService.findTop(0L);
        model.addAttribute("hxf2",topOrganizations);
        return "organization/organizationRegister";
    }
    @RequestMapping("/toLogin")
    public String toLogin(Model model)
    {
        return "organization/login";
    }
    @RequestMapping("/login")
    public void login(String email,
                      String password,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        //
        Organization organization = organizationService.checkLogin(email, password);
        if (organization != null) {
            //登录成功 重定向到首页
            request.getSession().setAttribute("organization", organization);
            response.sendRedirect("/organization/index");
        } else {
            log.info("登录失败");
            request.getSession().setAttribute("error","登录失败，邮箱或者密码错误");
            response.sendRedirect("/organization/error");
       //     throw new LoginException("登录失败！ 邮箱或者密码错误");
        }
    }
    @RequestMapping("/register")
    private String register(HttpServletRequest request, Organization organization)
    {
        //获取父组织
        String parentName= HttpServletRequestUtil.getString(request,"parent");
        log.info(organization.getPassword());
        List<Organization> parent=organizationService.findParent(parentName);
        if(parent.size()>0)
        {
            organization.setParentId(parent.get(0).getOrganizationId());
        }
        //获取审核老师
        String teacher=HttpServletRequestUtil.getString(request,"teacher");
        User teacher2=userService.findUserByName(teacher);
        if(teacher2!=null)
        {
            ManagerOrganization managerOrganization= ManagerOrganization.builder().organization(organization).user(teacher2).grade(1).createTime(Calendar.getInstance()).build();
            managerOrganizationService.save(managerOrganization);
        }
        //设置插入时间
        organization.setCreateTime(Calendar.getInstance());
        //插入数据库
        organizationService.save(organization);
        return "redirect:/organization/index";
    }
    /**
     * 登出
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("organization");
        response.sendRedirect("/organization/index");
    }

    /**
     * 验证用户名是否唯一
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkOrganizationName")
    public ResultBean<Boolean> checkUsername(String organizationName){
        List<Organization> users = organizationService.findByOrganizationName(organizationName);
        if (users==null||users.isEmpty()){
            return new ResultBean<>(true);
        }
        return new ResultBean<>(false);
    }

    @RequestMapping("/toApply")
    public String toApply()
    {
        return "organization/activityApply";
    }
    @RequestMapping("/apply")
    public String apply(Activity activity, HttpServletRequest request)
    {
        log.info("活动申请：");
        return "redirect:/organization/index";
    }
    @RequestMapping("/toCompose")
    public String toCompose()
    {
        return "organization/activityCompose";
    }
    @RequestMapping("/compose")
    public void compose()
    {

    }
    @RequestMapping("/noticeDetail")
    public String noticeDetail()
    {
        return "organization/noticeDetail";
    }
    @RequestMapping("/toPostNotice")
    public String toPostNotice()
    {
        return "organization/noticePost";
    }
    @RequestMapping("/noticePost")
    public void noticePost(Notice notice,HttpServletRequest request,HttpServletResponse response)
    {
        try {
            response.sendRedirect("/organization/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/messageDetail")
    public String messageManager(String type)
    {
        return "organization/messageDetail";
    }
    @RequestMapping("/activityComment")
    public String activityComment()
    {
        return "organization/activityComment";
    }
}
