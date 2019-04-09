package com.example.test1.demo.service.serviceImp;

import com.example.test1.demo.Model.Organization;

import java.util.List;

public interface OrganizationImp {
    List<Organization> findTop(long id);
    List<Organization> findParent(String name);
    public void save(Organization organization);
}
