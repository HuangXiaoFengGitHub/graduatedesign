package com.example.graduatedesign.controller.organization;

import com.example.graduatedesign.Model.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/organization/noticeManage")
@Slf4j
public class OrganizationNoticeController {
    @RequestMapping("/noticeDetail")
    public String noticeDetail()
    {
        return "organization/noticeDetail";
    }
    @RequestMapping("/toPostNotice")
    public String toPostNotice()
    {
        return "organization/noticePost";
    }
    @RequestMapping("/noticePost")
    public void noticePost(Notice notice, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            response.sendRedirect("/organization/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
