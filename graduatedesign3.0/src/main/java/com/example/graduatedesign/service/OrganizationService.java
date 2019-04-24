package com.example.graduatedesign.service;


import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.dao.OrganizationRepository;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.OrganizationState;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.serviceImp.OrganizationImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class OrganizationService implements OrganizationImp {
    @Autowired
    OrganizationRepository organizationRepository;
    public  List<Organization> findTop(long id)
    {
        return organizationRepository.findByParentId(id);
    }
    public List<Organization> findParent(String name)
    {
        return organizationRepository.findByOrganizationName(name);
    }
    public void save(Organization organization)
    {
        organizationRepository.save(organization);
    }
    public Organization checkLogin(String email,String password)
    {
        return organizationRepository.findByEmailAndPassword(email, password);
    }
    public List<Organization> findByOrganizationName(String organizationName){
        return organizationRepository.findByOrganizationName(organizationName);
    }
   public List<Organization> findOrganizationsByName(String name)
    {
        return organizationRepository.findOrganizationsByOrganizationNameContaining(name);
    }
    public OrganizationExecution register(Organization organization, MultipartFile organizationImg, MultipartFile wechatImg)
    {
        return new OrganizationExecution(OrganizationState.SUCCESS,organization);
    }
    //public void addProfileImg(User user, CommonsMultipartFile profileImg);
    public OrganizationExecution modifyUser(Organization organization,MultipartFile organizationImg,MultipartFile wechatImg){
        return new OrganizationExecution(OrganizationState.SUCCESS,organization);
    }
}
