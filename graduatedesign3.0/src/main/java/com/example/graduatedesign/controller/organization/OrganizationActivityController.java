package com.example.graduatedesign.controller.organization;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.Result;
import com.example.graduatedesign.service.ActivityCategoryService;
import com.example.graduatedesign.service.ActivityService;
import com.example.graduatedesign.service.PlaceService;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/organization/activityOperate")
public class OrganizationActivityController {
    @Autowired
    ActivityCategoryService activityCategoryService;
    @Autowired
    PlaceService placeService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> create(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //接收编辑器的内容，作为文章id
   //     log.info(activity.toString());
        Map<String,Object> map=new HashMap<>();
        log.info("创建活动:");
//        String startTime= HttpServletRequestUtil.getString(request,"startTime1");
//        String endTime=HttpServletRequestUtil.getString(request,"endTime1");
        String placestr=HttpServletRequestUtil.getString(request,"place");
        String categorystr=HttpServletRequestUtil.getString(request,"activityCategory");
        String activityStr=HttpServletRequestUtil.getString(request,"activity");
        Organization organization=(Organization) request.getSession().getAttribute("organization");
        if(organization==null)
        {
            map.put("success",false);
            map.put("errMsg","未登录！");
            return  map;
        }
        log.info(activityStr);
        Activity activity=null;
        try {
            if(activityStr!=null&&activityStr.trim()!="") {
                activity = objectMapper.readValue(activityStr, Activity.class);
                log.info("转换成功");
            }
            else {
                map.put("success",false);
                map.put("errMsg","传入了空的活动信息");
            }
        } catch (IOException e) {
            map.put("success",false);
            map.put("errMsg",e.toString());
            return map;
        }
        if(StringUtils.isNotBlank(activity.getActivityName()) && StringUtils.isNotBlank(activity.getActivityDesc()) && activity.getStartTime()!=null && activity.getEndTime()!=null )
        {
            Place place=placeService.findByPlaceName(placestr);
            ActivityCategory activityCategory=activityCategoryService.findByName(categorystr);
            log.info(placestr);
            log.info(categorystr);
            log.info(place.toString());
            log.info(activityCategory.toString());
            ActivityExecution activityExecution=activityService.applyActivity(organization.getOrganizationId(),activity,place,activityCategory);
            if(activityExecution.getState()==1)
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg",activityExecution.getStateInfo());
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","申请信息不全！请检查！");
        }
//        Calendar start=Calendar.getInstance();
//        Calendar end=Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date startdate = dateFormat.parse(startTime);
//        Date enddate=dateFormat.parse(endTime);
//        start.setTime(startdate);
//        end.setTime(enddate);
//        log.info(startTime);
//        log.info(endTime);
        return map;
    }
    @RequestMapping("/getCheckActivity")
    @ResponseBody
    public Map<String,Object> getCheckActivity(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<>();
        log.info("获取通过审核的活动：");
        Organization organization=(Organization) request.getSession().getAttribute("organization");
        if(organization!=null) {
            log.info(organization.getOrganizationName());
            ActivityExecution activityExecution = activityService.findCheckedActivity(organization.getOrganizationId(),3);
            if (activityExecution.getState() == 1) {
                log.info(activityExecution.getActivityList().size()+"");
                map.put("activityList", activityExecution.getActivityList());
                map.put("success",true);
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","你尚未登录");
        }
        return map;
    }

    /**
     * 获取该组织下的所有活动
     * @param request
     * @return
     */
    @RequestMapping("/getAllActivity")
    @ResponseBody
    public Map<String,Object> getAllActivity(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<>();
        log.info("获取申请组织：");
        Organization organization=(Organization) request.getSession().getAttribute("organization");
        if(organization!=null) {
            log.info(organization.getOrganizationName());
            ActivityExecution activityExecution = activityService.findActivityByOrganization(organization.getOrganizationId());
            if (activityExecution.getState() == 1) {
                log.info(activityExecution.getActivityList().size()+"");
                map.put("activityList", activityExecution.getActivityList());
                map.put("success",true);
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","你尚未登录");
        }
        return map;
    }
    /**
     * 根据活动id返回活动
     * @param activityId
     * @return
     */
    @RequestMapping("/getActivityDetail")
    @ResponseBody
    public Result<Activity> getActivityDetail(@RequestParam(defaultValue = "0") long activityId)
    {
        Activity activity=activityService.findActivityById(activityId);
        if(activity!=null)
            return new Result<>(true,activity);
        else
            return new Result<>(false,1,"找不到活动");
    }

    @RequestMapping("/editActivity")
    public void editActivity()
    {

    }
    @RequestMapping("/compose")
    public void compose()
    {

    }
}
