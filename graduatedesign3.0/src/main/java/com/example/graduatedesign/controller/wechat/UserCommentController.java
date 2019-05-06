package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.service.CommentService;
import com.example.graduatedesign.service.UserService;
import com.example.graduatedesign.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/user/comment")
public class UserCommentController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @RequestMapping("/like")
    @ResponseBody
    public Map<String,Object> likeComment(@RequestParam(value = "commentId",defaultValue = "0") long commentId, HttpServletRequest request)
    {
        log.info("赞："+commentId);
        Map<String,Object> map=new HashMap<>();
        //User currentUser=(User) request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(9L);
        if(commentId >0 )
        {
          Boolean success =userService.addLikeComment(currentUser,commentId,true);
            if(success)
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","不能重复点赞");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择评论");
        }
        return map;
    }
    @RequestMapping("/cancelLike")
    @ResponseBody
    public Map<String,Object> cancelLikeActivity(@RequestParam(value = "commentId",defaultValue = "0") long commentId,HttpServletRequest request)
    {
        log.info("取消赞："+commentId);
        Map<String,Object> map=new HashMap<>();
        //User currentUser=(User) request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(1L);
        if(commentId >0 )
        {
            Boolean success =userService.addLikeComment(currentUser,commentId,false);
            if(success)
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","操作失败");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择评论");
        }
        return map;
    }
    @RequestMapping("/toPost")
    public String toPost(@RequestParam(defaultValue = "0") long activityId)
    {
        return "frontend/topostcomment";
    }
    @RequestMapping("/post")
    @ResponseBody
    public Map<String,Object> postComment(@RequestParam(value = "activityId",defaultValue = "0") long activityId, HttpServletRequest request)
    {
        log.info("评论："+activityId);
        Map<String,Object> map=new HashMap<>();
        //User currentUser=(User) request.getSession().getAttribute("user");
        User currentUser=userService.findUserByUserId(1L);
        String content= HttpServletRequestUtil.getString(request,"comment");

        if(activityId >0 )
        {
            Boolean success =commentService.postComment(content,currentUser.getUserId(),activityId);
            if(success)
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","添加失败,评论微信为空");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择活动");
        }
        return map;
    }

}
