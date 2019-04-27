package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.service.*;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    UserService userService;
    @Autowired
    ActivityService activityService;
    @Autowired
    AccountService accountService;
    @Autowired
    TagsService tagsService;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    ActivityCategoryService activityCategoryService;
    @RequestMapping("/getActivityDetail")
    public String getActivityDetail(@RequestParam(value = "activityId")long activityId)
    {
        log.info("activityId:"+activityId+"");
        return "wechat/activityDetail";
    }

    /**
     * 获取活动列表
     * 组合查询 活动类别 活动标签 活动时间 校园组织 活动状态 活动名称
     * @param activityCategoryId
     * @return
     */
    @RequestMapping("/getActivityList")
    @ResponseBody
    public Map<String,Object> getActivityList(@RequestParam(defaultValue = "0") long activityCategoryId,HttpServletRequest request)
    {
//        activityService.findByCategoryPage()
        Map<String,Object> map=new HashMap<>();
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        //比如说，输入第一也，pageindex=0，这是为了适应Pageable对象
        pageIndex--;
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        //从前端获取父类别id
       // List<Tags> tags=null;
        //仅获取该类别下的活动
//        if(activityCategoryId!=0)
//        {
        try {
            Activity model = new Activity();
            //匹配搜索关键字
            String organizationName = HttpServletRequestUtil.getString(request, "keyword");
            if (StringUtils.isNotBlank(organizationName)) {
                //首先搜索组织名称
                Organization organization = organizationService.findByOrganizationNameContain(organizationName);
                if (organization != null) {
                    model.setOrganization(organization);
                }
                //搜索活动名称
                else {
                    model.setActivityName(organizationName);
                }

            }
            //匹配活动类别
            Optional<ActivityCategory> activityCategory = activityCategoryService.findById(activityCategoryId);
            if (activityCategory.isPresent())
                model.setCategory(activityCategory.get());
            //匹配活动标签
            long tagId = HttpServletRequestUtil.getLong(request, "tagId");
            Tags tags = tagsService.findTagsById(tagId);
            if (tags != null)
                model.setTags(tags);
            //匹配活动状态
            String activityState = HttpServletRequestUtil.getString(request, "status");
            if (StringUtils.isNotBlank(activityState)) {
                model.setStatus(activityState);
            }
            //前端展示的活动都是审核成功的活动列表
            model.setEnableStatus(1);
            //根据查询条件取出查询出来的活动
           ActivityExecution activityExecution = activityService.findSearch(model, pageIndex, pageSize);
           //  ActivityExecution activityExecution = activityService.findAll(pageIndex, pageSize);
            map.put("activityList", activityExecution.getActivityList());
            map.put("count", activityExecution.getCount());
            map.put("success", true);
        }
        catch (Exception e)
        {
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
//        }
//        //获取全部活动
//        else {
//
//
//        }
        return map;
    }
    /**
     * 获取活动类别下的标签
     * @param activityCategoryId
     * @return
     */
    @RequestMapping(value = "/getActivityTags",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getActivityTags(@RequestParam(defaultValue = "0") long activityCategoryId)
    {
        Map<String,Object> map=new HashMap<>();
        //从前端获取父类别id
        List<Tags> tags=null;
        //仅获取对应类别下的标签,用户在首页选择全部活动列表。
        if(activityCategoryId!=0)
        {
            try{
                tags=tagsService.findByCategory(activityCategoryId);
                map.put("success",true);
                map.put("tagList",tags);
            }
            catch (Exception e)
            {
                map.put("success",false);
                map.put("errMsg",e.getMessage());
            }
        }
        //获取全部标签
        else {
            try{
            tags=tagsService.findAll();
            map.put("success",true);
            map.put("tagList",tags);
            }
            catch (Exception e)
            {
                map.put("success",false);
                map.put("errMsg",e.getMessage());
            }
        }
        return map;
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
