package com.example.graduatedesign.controller.organization;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.Model.pojo.ResultBean;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.exception.LoginException;
import com.example.graduatedesign.service.ManagerOrganizationService;
import com.example.graduatedesign.service.OrganizationCategoryService;
import com.example.graduatedesign.service.OrganizationService;
import com.example.graduatedesign.service.UserService;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    OrganizationCategoryService organizationCategoryService;
    @RequestMapping("/index")
    public String index(Model model,HttpServletRequest request)
    {
        Organization organization=(Organization) request.getSession().getAttribute("organization");
        if(organization!=null)
        {
            int activityNumber=organizationService.countAllActivity(organization.getOrganizationId());
            int checking=organizationService.countActivityByStatus(organization.getOrganizationId(),1);
            int going=organizationService.countActivityByStatus(organization.getOrganizationId(),5);
            long like=organizationService.countMyUsers(organization.getOrganizationId());
            model.addAttribute("activityNumber",activityNumber);
            model.addAttribute("checking",checking);
            model.addAttribute("going",going);
            model.addAttribute("like",like);
        }
        else {
            model.addAttribute("activityNumber",0);
            model.addAttribute("checking",0);
            model.addAttribute("going",0);
            model.addAttribute("like",0);
        }
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
        //将父组织信息返回给前台
        List<Organization> topOrganizations=organizationService.findTop(0L);
        List<OrganizationCategory> organizationCategories=organizationCategoryService.findAll();
        model.addAttribute("hxf2",topOrganizations);//通过model返回给前台
        model.addAttribute("category",organizationCategories);
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
        log.info(email);
        log.info(password);
        OrganizationExecution organization = organizationService.checkLogin(email, password);
        if (organization.getState() == 1) {
            //登录成功 重定向到首页
            request.getSession().setAttribute("organization", organization.getOrganization());
            response.sendRedirect("/organization/index");
        } else {
            log.info("登录失败");
            request.getSession().setAttribute("error","登录失败，邮箱或者密码错误");
            response.sendRedirect("/organization/error");
       //     throw new LoginException("登录失败！ 邮箱或者密码错误");
        }
    }
    @RequestMapping("/register")
    private String register(HttpServletRequest request, Organization organization,@RequestParam("organizationImg2") MultipartFile organizationImg,@RequestParam("wechatImg2") MultipartFile wechatImg)
    {
        //获取父组织
        String parentName= HttpServletRequestUtil.getString(request,"parent2");//后台直接获取form的name值即可
        log.info(parentName);
        List<Organization> parent=organizationService.findParent(parentName);
        if(parent.size()>0)
        {
            organization.setParentId(parent.get(0).getOrganizationId());
        }
        //获取审核老师
        String teacher=HttpServletRequestUtil.getString(request,"teacher");
        User teacher2=userService.findManagerByName(teacher);
        log.info(teacher2.getUserDesc());
        if(teacher2!=null)
        {
            ManagerOrganization managerOrganization= ManagerOrganization.builder().organization(organization).user(teacher2).grade(1).createTime(Calendar.getInstance()).build();
            managerOrganizationService.save(managerOrganization);
        }
        //获取组织类别
        String category=HttpServletRequestUtil.getString(request,"category");
        OrganizationCategory organizationCategory=organizationCategoryService.findByName(category);
        if(organizationCategory!=null)
            organization.setOrganizationCategory(organizationCategory);
        //插入数据库
        log.info(organization.getPassword());
        OrganizationExecution organizationExecution=organizationService.register(organization,organizationImg,wechatImg);
        return "redirect:/organization/toLogin";
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
