package com.example.graduatedesign.service.serviceImp;



import com.example.graduatedesign.Model.Organization;

import java.util.List;

public interface OrganizationImp {
    List<Organization> findTop(long id);
    List<Organization> findParent(String name);
    public void save(Organization organization);
}
