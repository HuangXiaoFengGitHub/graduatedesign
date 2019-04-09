package com.example.test1.demo.dao;

import com.example.test1.demo.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    List<Organization> findByParentId(long id);

    List<Organization> findByOrganizationName(String name);

}
