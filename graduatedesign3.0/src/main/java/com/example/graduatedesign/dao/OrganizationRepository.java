package com.example.graduatedesign.dao;


import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.Organization;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization,Long>, JpaSpecificationExecutor<Organization> {

    Organization findByOrganizationId(long id);

    List<Organization> findByParentId(long id);

    List<Organization> findByOrganizationName(String name);

    List<Organization> findByOrganizationNameLike(String name);

    Organization findByEmailAndPassword(String email,String password);

    //模糊查询
    List<Organization> findOrganizationsByOrganizationNameContaining(String name);

    Organization findByOrganizationNameContaining(String name);

}
