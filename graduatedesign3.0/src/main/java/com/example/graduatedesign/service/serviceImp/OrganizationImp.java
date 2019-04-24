package com.example.graduatedesign.service.serviceImp;



import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.dto.UserExecution;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrganizationImp {
    List<Organization> findTop(long id);
    List<Organization> findParent(String name);
    public void save(Organization organization);
    Organization checkLogin(String email,String password);
    List<Organization> findOrganizationsByName(String name);
    public OrganizationExecution register(Organization organization, MultipartFile organizationImg, MultipartFile wechatImg);
    //    public void addProfileImg(User user, CommonsMultipartFile profileImg);
    public OrganizationExecution modifyUser(Organization organization,MultipartFile organizationImg,MultipartFile wechatImg);
}
