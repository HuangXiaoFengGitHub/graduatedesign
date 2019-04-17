package com.example.graduatedesign.dao;


import com.example.graduatedesign.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    List<Organization> findByParentId(long id);

    List<Organization> findByOrganizationName(String name);

    Organization findByEmailAndPassword(String email,String password);

}
