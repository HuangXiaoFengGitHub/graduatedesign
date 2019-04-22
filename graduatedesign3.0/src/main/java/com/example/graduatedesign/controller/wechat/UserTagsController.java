package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.Result;
import com.example.graduatedesign.service.TagsService;
import com.example.graduatedesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping("/user/tags")
public class UserTagsController {
    @Autowired
    UserService userService;
    @Autowired
    TagsService tagsService;
    @RequestMapping(value = "getMyTags",method = RequestMethod.GET)
    @ResponseBody
    public Result<Set<Tags>> getMyTags(HttpServletRequest request)
    {
        log.info("get My Tags:");
        Result<Set<Tags>> result;
        //   User currentUser=(User) request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(1L);
        log.info("get User:"+currentUser.toString());
        List<Tags> tagss=tagsService.findAll();
        log.info("tagss:"+tagss.toString());
        currentUser.getTags().addAll(tagss);
        log.info("currentUser.getTags()"+currentUser.getTags().toString());
        if(currentUser!=null)
        {
            Set<Tags> tags=userService.findMyTags(currentUser);
            log.info("tags:"+tags.toString());
            if(tags !=null)
            {
                result=new Result(true,tags);

            }
            else
            {
                result=new Result(false,1,"用户没有设置任何标签");
            }
        }
        else
        {
            result=new Result(false,1,"你尚未登录");
        }
        return result;
    }
    @RequestMapping(value = "/addTags",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addTags(HttpServletRequest request, @RequestBody List<Tags> tags)
    {
        log.info("add Tags：");
        Map<String,Object> map=new HashMap<>();
        //   User currentUser=(User)request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(1L);
        log.info("currentUser:"+currentUser.toString());
        log.info("tags:"+tags.toString());

        if(currentUser!=null)
        {
            User newUser=userService.findUserByUserId(currentUser.getUserId());
            log.info(newUser.toString());
            userService.addMyTags(newUser,tags);
            log.info(newUser.toString());
            map.put("success",true);
        }
        else {
            map.put("success",false);
            map.put("errMsg","你尚未登录");
        }
        return map;
    }
    @RequestMapping(value = "/removeTags",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> remove(HttpServletRequest request,Long tagId)
    {
        log.info("begin delete tags:");
        Map<String,Object> map=new HashMap<>();
        if(tagId!=null)
        {
            //  User currentUser=(User)request.getSession().getAttribute("user");
            User currentUser=userService.findUserByUserId(1L);
            log.info(currentUser.getTags().toString());
            Tags tag=tagsService.findTagsById(tagId);
            log.info(tag.toString());
            User newUser=userService.findUserByUserId(currentUser.getUserId());
            log.info(newUser.getTags().toString());
            if(newUser!=null)
            {
                newUser.getTags().remove(tag);
                //更新User对象
                userService.save(newUser);
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","找不到用户信息");
            }
        }
        else {
            map.put("success",false);
            map.put("errMsg","请选择至少一个标签");
        }
        return  map;
    }
}
