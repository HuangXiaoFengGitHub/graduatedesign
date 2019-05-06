package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.service.*;
import com.example.graduatedesign.util.CommonUtil;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/user/organizationOperate")
public class UserOrganizationOperationController {
    @Autowired
    UserService userService;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    ActivityCategoryService activityCategoryService;
    @Autowired
    TagsService tagsService;
    @Autowired
    OrganizationCategoryService organizationCategoryService;
    @RequestMapping(value = "/addLikeOrganization")
    @ResponseBody
    public Map<String,Object> addLikeOrganization(@RequestParam(value = "organizationId",defaultValue = "0") long organizationId, HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<>();
        //User currentUser=(User) request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(1L);
        if(organizationId >0 )
        {
            OrganizationExecution organizationExecution =userService.addMyLikeOrganization(currentUser,organizationId,true);
            if(organizationExecution.getState()==1)
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg",organizationExecution.getStateInfo());
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择组织");
        }
        return map;
    }
    @RequestMapping("/cancelLikeOrganization")
    @ResponseBody
    public Map<String,Object> cancelLikeActivity(@RequestParam(value = "organizationId",defaultValue = "0") long organizationId, HttpServletRequest request)
    {
        log.info("取消关注2：");
        log.info(organizationId+"");
        Map<String,Object> map=new HashMap<>();
        //User currentUser=(User) request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(1L);
        log.info(currentUser.getLikeActivities().toString());
        if(organizationId >0 )
        {
            OrganizationExecution organizationExecution =userService.addMyLikeOrganization(currentUser,organizationId,false);
            log.info(currentUser.getLikeActivities().toString());
            if(organizationExecution.getState()==1)
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg",organizationExecution.getStateInfo());
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择组织");
        }
        return map;
    }
    /**
     * 组织详情界面，根据organizationId 获取界面需要的信息。
     * @param organizationId
     * @return
     */
    @RequestMapping("/getOrganization")
    @ResponseBody
    public Map<String,Object> getOrganization(@RequestParam(defaultValue = "0") long organizationId)
    {
        log.info("根据组织id获取组织详情："+organizationId);
        Map<String,Object> map=new HashMap<>();
        // User user=(User)request.getSession().getAttribute("user");
        User user=userService.findUserByUserId(1L);
        boolean isLike=organizationService.isLike(organizationId,user);
        if(organizationId!=0)
        {
            try{
                Organization organization=organizationService.findById(organizationId);
                List<ActivityCategory> activityCategoryList=activityCategoryService.findAll();
                List<Tags> tags=organizationService.findAllTags(organizationId);
                log.info(tags.size()+"个标签");
                log.info(organization.toString());
                organization.setUpdateTime(Calendar.getInstance());
                organization.setCreateTime(Calendar.getInstance());
                //获取组织的上级组织
                Organization parentOrganization=organizationService.findById(organization.getParentId());
                //组织的封面图片
                organization.setOrganizationImg("\\upload\\images\\item\\activityinfo\\2019041721464945678.JPG");
                //组织的微信二维码
                organization.setWechatImg("\\upload\\images\\item\\activityinfo\\2019041721464945678.JPG");
                //更新一下咯
                organizationService.save(organization);
                map.put("success",true);
                map.put("isLike",isLike);
                map.put("organization",organization);
                map.put("activitycategorylist",activityCategoryList);
                map.put("tagsList",tags);
                map.put("parentOrganization",parentOrganization);
            }
            catch (Exception e)
            {
                map.put("success",false);
                map.put("errMsg",e.toString());
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","未输入组织id");
        }
        return map;
    }
    /**
     * 组织列表界面
     * 获取组织列表
     * 组合查询 组织类别 组织名称 组织简介
     * @param
     * @return
     */
    @RequestMapping("/getOrganizationList")
    @ResponseBody
    public Map<String,Object> getOrganizationList(@RequestParam(defaultValue = "0") long organizationCategoryId, HttpServletRequest request)
    {
        log.info("获取组织列表：");
//        activityService.findByCategoryPage()
        Map<String,Object> map=new HashMap<>();
        int pageIndex= HttpServletRequestUtil.getInt(request,"pageIndex");
        //比如说，输入第一页，pageindex=0，这是为了适应Pageable对象
        pageIndex--;
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        try {
            Organization model = new Organization();
            //匹配搜索关键字
            String keyword = HttpServletRequestUtil.getString(request, "keyword");
            log.info(keyword);
            //匹配活动类别，如果没有定义活动类别,则activityCategoryId==0，则就匹配全部活动
            if(organizationCategoryId!=0)
            {
                Optional<OrganizationCategory> organizationCategory = organizationCategoryService.findById(organizationCategoryId);
                if (organizationCategory.isPresent())
                    model.setOrganizationCategory(organizationCategory.get());
            }
            //匹配活动状态
            //前端展示的活动都是审核成功的活动列表
            model.setEnableStatus(1);
            //根据查询条件取出查询出来的活动
            OrganizationExecution organizationExecution= organizationService.findSearch(model, pageIndex, pageSize,keyword);
            //  ActivityExecution activityExecution = activityService.findAll(pageIndex, pageSize);
            if(organizationExecution.getState()==1) {
                map.put("organizationList", organizationExecution.getShopList());
                map.put("count", organizationExecution.getCount());
                map.put("success", true);
            }
            else {
                map.put("success",false);
                map.put("errMsg",organizationExecution.getStateInfo());
            }
        }
        catch (Exception e)
        {
            map.put("success",false);
            map.put("errMsg",e.toString());
        }
        return map;
    }
}
