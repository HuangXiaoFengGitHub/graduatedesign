package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.OrganizationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationCategoryRepository extends JpaRepository<OrganizationCategory,Long> {
  OrganizationCategory findByOrganizationCategoryId(long id);
  OrganizationCategory findByOrganizationCategoryName(String name);
}
