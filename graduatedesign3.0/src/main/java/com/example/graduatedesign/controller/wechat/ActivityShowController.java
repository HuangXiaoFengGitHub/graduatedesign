package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.service.*;
import com.example.graduatedesign.util.CommonUtil;
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
public class ActivityShowController {
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
    @Autowired
    NoticeService noticeService;
    @Autowired
    ActivityCommentService activityCommentService;
    /**
     * 活动详情界面
     * @param activityId
     * @return
     */
    @RequestMapping("/getActivityDetail")
    public String getActivityDetail(@RequestParam(defaultValue = "0")long activityId)
    {
        log.info("activityId:"+activityId+"");
        return "frontend/activitydetail";
    }

    /**
     * 活动详情界面，调用
     * @param activityId
     * @param request
     * @return
     */
    @RequestMapping("/getActivity")
    @ResponseBody
    public Map<String,Object> getActivity(@RequestParam(defaultValue = "0")long activityId,HttpServletRequest request)
    {
        log.info("根据活动id获取活动详情："+activityId);
        Map<String,Object> map=new HashMap<>();
       // User user=(User)request.getSession().getAttribute("user");
        User user=userService.findUserByUserId(1L);
        boolean isLike=userService.isLike(activityId,user);
        boolean isSignUp=userService.isSignUp(activityId,user);
        if(activityId!=0)
        {
            try{
                Activity activity=activityService.findActivityById(activityId);
                log.info(activity.getActivityImgs().toString());
                activity.setUpdateTime(Calendar.getInstance());
                activity.setStartTime(Calendar.getInstance());
                activity.setEndTime(Calendar.getInstance());
                activity.setImgAddr("\\upload\\images\\item\\activityinfo\\2019041721464945678.JPG");
                activityService.save(activity);
                log.info(activity.getActivityId()+"");
                map.put("count",activity.getActivityImgs().size());
                map.put("success",true);
                map.put("isLike",isLike);
                map.put("isSignUp",isSignUp);
                map.put("activity",activity);
            }
            catch (Exception e)
            {
                map.put("success",false);
                map.put("errMsg",e.getMessage());
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","未输入活动id");
        }
        return map;
    }

    /**
     * 活动列表界面
     * 获取活动列表
     * 组合查询 活动类别 活动标签 活动时间 校园组织 活动状态 活动名称
     * @param activityCategoryId
     * @return
     */
    @RequestMapping("/getActivityList")
    @ResponseBody
    public Map<String,Object> getActivityList(@RequestParam(defaultValue = "0") long activityCategoryId,@RequestParam(defaultValue = "0") long organizationId, HttpServletRequest request)
    {
        log.info("获取活动列表：");
//        activityService.findByCategoryPage()
        Map<String,Object> map=new HashMap<>();
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        //比如说，输入第一页，pageindex=0，这是为了适应Pageable对象
        pageIndex--;
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        //从前端获取父类别id
       // List<Tags> tags=null;
        //仅获取该类别下的活动
//        if(activityCategoryId!=0)
//        {
        try {
            Activity model = new Activity();
            //匹配组织id
            if(organizationId!=0L)
            {
                Organization organization=organizationService.findById(organizationId);
                model.setOrganization(organization);
            }
            //匹配搜索关键字
            String keyword = HttpServletRequestUtil.getString(request, "keyword");
            if (StringUtils.isNotBlank(keyword)) {
                //首先搜索组织名称
                // TO DO 这里有bug，如果查找出很多个组织就凉了
                Organization organization = organizationService.findByOrganizationNameContain(keyword);
                //前面的组织id已经确定组织了，如果没有传入组织id，再匹配组织名称
                if (organization != null && model.getOrganization()==null) {
                    model.setOrganization(organization);
                }
                //搜索活动名称
                else {
                    model.setActivityName(keyword);
                    model.setActivityDesc(keyword);
                }
            }
            //匹配活动类别，如果没有定义活动类别,则activityCategoryId==0，则就匹配全部活动
            if(activityCategoryId!=0)
            {
                Optional<ActivityCategory> activityCategory = activityCategoryService.findById(activityCategoryId);
                if (activityCategory.isPresent())
                    model.setCategory(activityCategory.get());
            }
            //匹配活动标签
            long tagId = HttpServletRequestUtil.getLong(request, "tagId");
            Tags tags = tagsService.findTagsById(tagId);
            if (tags != null)
                model.setTags(tags);
            //匹配活动状态
            String activityState = HttpServletRequestUtil.getString(request, "activitystatus");
            //s全部活动为""
            if (StringUtils.isNotBlank(activityState)) {
                int state= CommonUtil.activityStateChange(activityState);
                model.setStatus(state);
            }
            //前端展示的活动都是审核成功的活动列表
            model.setEnableStatus(1);
            //根据查询条件取出查询出来的活动
           ActivityExecution activityExecution = activityService.findSearch(model, pageIndex, pageSize,keyword);
           //  ActivityExecution activityExecution = activityService.findAll(pageIndex, pageSize);
            if(activityExecution.getState()==1) {
                map.put("activityList", activityExecution.getActivityList());
                map.put("count", activityExecution.getCount());
                map.put("success", true);
            }
            else {
                map.put("success",false);
                map.put("errMsg",activityExecution.getStateInfo());
            }
        }
        catch (Exception e)
        {
            map.put("success",false);
            map.put("errMsg",e.toString());
        }
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
     * 活动详情界面，
     * 获取活动公告
     * @param activityId
     * @return
     */
    @RequestMapping("/getActivityNotice")
    @ResponseBody
    public Map<String ,Object> getNotice(@RequestParam(defaultValue = "0")long activityId)
    {
        Map<String,Object> map=new HashMap<>();
        if(activityId>0) {
            List<Notice> notices=noticeService.findByActivity(activityId);
            if(notices!=null)
            {
                map.put("success",true);
                map.put("noticeList",notices);
            }
            else {
                map.put("success",false);
                map.put("errMsg","找不到活动公告");
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","找不到活动");
        }
        return map;
    }
    @RequestMapping("/getActivityComment")
    @ResponseBody
    public Map<String,Object> getComment(@RequestParam(defaultValue = "0")long activityId,HttpServletRequest request)
    {
        log.info("获取活动评论");
        Map<String,Object> map=new HashMap<>();
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        //比如说，输入第一页，pageindex=0，这是为了适应Pageable对象
        pageIndex--;
        log.info("pageIndex:"+pageIndex);
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        log.info("pageSize:"+pageSize+"");
        if(activityId>0) {
            List<UserActivityComment> comments=activityCommentService.findCommentByActivity(activityId,pageIndex,pageSize);
            if(comments!=null)
            {
                map.put("success",true);
                map.put("commentList",comments);
            }
            else {
                map.put("success",false);
                map.put("errMsg","没有传入页码，找不到活动评论");
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","找不到活动");
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
