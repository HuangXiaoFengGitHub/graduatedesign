package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.OrganizationCategory;
import com.example.graduatedesign.dao.ActivityCategoryRepository;
import com.example.graduatedesign.dao.OrganizationCategoryRepository;
import com.example.graduatedesign.service.serviceImp.ActivityCategoryServiceImp;
import com.example.graduatedesign.service.serviceImp.OrganizationCategoryServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrganizationCategoryService implements OrganizationCategoryServiceImp {
    @Autowired
    OrganizationCategoryRepository organizationCategoryRepository;
    public List<OrganizationCategory> findAll()
    {
        return organizationCategoryRepository.findAll();
    }
    public Optional<OrganizationCategory> findById(long id)
    {
        return organizationCategoryRepository.findById(id);
    }
    public OrganizationCategory findByName(String name)
    {
        return organizationCategoryRepository.findByOrganizationCategoryName(name);
    }

}
