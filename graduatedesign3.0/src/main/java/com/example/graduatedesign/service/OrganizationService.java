package com.example.graduatedesign.service;


import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.dao.OrganizationRepository;
import com.example.graduatedesign.service.serviceImp.OrganizationImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
