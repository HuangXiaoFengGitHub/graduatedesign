package com.example.graduatedesign.controller.wechat;

import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user/organizationOperate")
public class UserOrganizationOperationController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/addLikeOrganization")
    public Map<String,Object> addLikeOrganization(@RequestParam(value = "organizationId",defaultValue = "0") long organizationId, HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<>();
        User currentUser=(User) request.getSession().getAttribute("user");
        if(organizationId >0 )
        {
            User user=userService.addMyLikeOrganization(currentUser,organizationId,true);
            if(user!=null && user.getUserId()==currentUser.getUserId())
            {
                map.put("success",true);
            }
            else {
                map.put("success",false);
                map.put("errMsg","添加失败");
            }
        }
        else
        {
            map.put("success",false);
            map.put("errMsg","未选择组织");
        }
        return map;
    }
}
