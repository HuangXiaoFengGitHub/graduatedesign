package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.ManagerOrganization;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.User;

import java.util.List;

public interface ManagerOrganizationImp {
    public void save(ManagerOrganization managerOrganization);
    //管理的组织数量
    int countAllByUser(long userId);
    List<ManagerOrganization> findByUser(long userId);
    List<ManagerOrganization> findByUserAndGrade(long userId,int grade);
    List<ManagerOrganization> findByOrganizationAndGrade(long organizationId, int grade);
}
