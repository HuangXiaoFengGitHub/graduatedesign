package com.example.graduatedesign.service.serviceImp;



import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.dto.UserExecution;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrganizationImp {
    Organization findById(long id);
    List<Organization> findTop(long id);
    List<Organization> findParent(String name);
    public long save(Organization organization);
    OrganizationExecution checkLogin(String email,String password);
    List<Organization> findOrganizationsByNameContain(String name);
    public OrganizationExecution register(Organization organization, MultipartFile organizationImg, MultipartFile wechatImg);
    //    public void addProfileImg(User user, CommonsMultipartFile profileImg);
    public OrganizationExecution modifyUser(Organization organization,MultipartFile organizationImg,MultipartFile wechatImg);
    boolean isLike(long organizationId,User user);
    List<Tags> findAllTags(long organizationId);
    public OrganizationExecution findSearch(Organization model, int pageIndex, int pageSize, String search);
    int countActivityByStatus(long organizationId,int status);
    long countMyUsers(long organizationId);
}
