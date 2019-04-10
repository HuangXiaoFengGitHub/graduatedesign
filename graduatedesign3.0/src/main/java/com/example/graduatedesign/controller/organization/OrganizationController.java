package com.example.graduatedesign.controller.organization;

import com.example.graduatedesign.Model.ManagerOrganization;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.service.ManagerOrganizationService;
import com.example.graduatedesign.service.OrganizationService;
import com.example.graduatedesign.service.UserService;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    ManagerOrganizationService managerOrganizationService;
    @Autowired
    UserService userService;
    @RequestMapping("/index")
    public String index()
    {
        return "organization/organizationIndex";
    }
    @RequestMapping("/toRegister")
    public String toRegister(Model model)
    {
        List<Organization> topOrganizations=organizationService.findTop(0L);
        model.addAttribute("hxf2",topOrganizations);
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
            ManagerOrganization managerOrganization= ManagerOrganization.builder().organization(organization).user(teacher2).grade(1).createTime(Calendar.getInstance()).build();
            managerOrganizationService.save(managerOrganization);
        }
        //设置插入时间
        organization.setCreateTime(Calendar.getInstance());
        //插入数据库
        organizationService.save(organization);
        return "redirect:/organization/index";
    }
}
