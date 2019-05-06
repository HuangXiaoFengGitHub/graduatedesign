package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.OrganizationCategory;

import java.util.List;
import java.util.Optional;

public interface OrganizationCategoryServiceImp {
    List<OrganizationCategory> findAll();
    Optional<OrganizationCategory> findById(long id);

}
