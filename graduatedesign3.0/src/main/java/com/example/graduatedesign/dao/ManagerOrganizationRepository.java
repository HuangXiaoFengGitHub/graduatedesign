package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ManagerOrganization;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ManagerOrganizationRepository extends JpaRepository<ManagerOrganization,Long>, JpaSpecificationExecutor<ManagerOrganization> {
    //管理的组织数量
    int countAllByUser(User user);
    List<ManagerOrganization> findByUser(User user);
    List<ManagerOrganization> findByUserAndGrade(User user,int grade);
    List<ManagerOrganization> findByOrganizationAndGrade(Organization organization, int grade);
}
