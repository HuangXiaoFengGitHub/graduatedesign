package com.example.test1.demo.web.controller.organization;

import com.example.test1.demo.Model.Organization;
import com.example.test1.demo.Model.User;
import com.example.test1.demo.service.OrganizationService;
import com.example.test1.demo.service.UserService;
import com.example.test1.demo.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;
    @Autowired
    UserService userService;
    @RequestMapping("/index")
    private String index()
    {
        return "organization/organizationIndex";
    }
    @RequestMapping("/toRegister")
    private String toRegister(Model model)
    {
        List<Organization> topOrganizations=organizationService.findTop(0L);
        User user=new User();
        Organization organization=new Organization();
        model.addAttribute("hxf",topOrganizations);
        model.addAttribute("organization",organization);
        model.addAttribute("user",user);
        return "organization/organizationRegister";
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

        }
        //设置插入时间
        organization.setCreateTime(Calendar.getInstance());
        //插入数据库
        organizationService.save(organization);
        return "redirect:/organization/index";
    }


}
