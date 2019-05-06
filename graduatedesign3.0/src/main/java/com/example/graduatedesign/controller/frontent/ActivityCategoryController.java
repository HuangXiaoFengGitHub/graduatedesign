package com.example.graduatedesign.controller.frontent;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.service.ActivityCategoryService;
import com.example.graduatedesign.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/frontend/activityCategory")

public class ActivityCategoryController {
    @Autowired
    ActivityCategoryService activityCategoryService;
    @Autowired
    TagsService tagsService;
    /**
     * 点击全部活动后，返回所有活动类别信息
     * @return
     */
    @RequestMapping("/getAllActivityCategory")
    @ResponseBody
    public Map<String,Object> getall()
    {
        Map<String,Object> map=new HashMap<>();
        List<ActivityCategory> activityCategoryList=null;
        try
        {
            activityCategoryList=activityCategoryService.findAll();
            map.put("activityCategoryList",activityCategoryList);
            map.put("success",true);
        }
        catch (Exception e)
        {
            map.put("success",false);
            map.put("errMsg",e.getMessage());
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
        log.info("获取"+activityCategoryId+"下的活动标签：");
        Optional<ActivityCategory> activityCategory=activityCategoryService.findById(activityCategoryId);
        Map<String,Object> map=new HashMap<>();
        //从前端获取父类别id
        List<Tags> tags=null;
        //仅获取对应类别下的标签,用户在首页选择全部活动列表。
        if(activityCategoryId!=0L)
        {
            try{
                tags=tagsService.findByCategory(activityCategoryId);
                map.put("success",true);
                map.put("activityCategoryName",activityCategory.get().getActivityCategoryName());
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
                map.put("activityCategoryName","全部活动");
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
}
