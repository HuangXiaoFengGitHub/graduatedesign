package com.example.graduatedesign.controller.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class wechatController {
    @RequestMapping("/index")
    public String index() {
        return "wechat/userIndex";
    }
}
