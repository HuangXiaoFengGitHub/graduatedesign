package com.example.test1.demo.service;

import com.example.test1.demo.Model.Organization;
import com.example.test1.demo.dao.OrganizationRepository;
import com.example.test1.demo.service.serviceImp.OrganizationImp;
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
}
