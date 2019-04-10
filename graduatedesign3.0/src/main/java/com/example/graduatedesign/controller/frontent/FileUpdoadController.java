package com.example.graduatedesign.controller.frontent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUpdoadController {

    @PostMapping("/form")
    public String handleFormUpload(@RequestParam("name") String name,
                                   @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // store the bytes somewhere
            return "redirect:uploadSuccess";
        }
        return "redirect:uploadFailure";
    }
//    <form method="post" action="upload" enctype="multipart/form-data">
//    File: <input type="file" name="file"/>
//  <input type="submit" value="Submit"/>
//   </form>

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public String upload(@RequestPart("file") MultipartFile multipartFile)
    {
        System.out.println(multipartFile.getOriginalFilename());
        return "redirect:/";
    }
}
