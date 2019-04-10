package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.ManagerOrganization;
import com.example.graduatedesign.dao.ManagerOrganizationRepository;
import com.example.graduatedesign.service.serviceImp.ManagerOrganizationImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerOrganizationService implements ManagerOrganizationImp {
    @Autowired
    ManagerOrganizationRepository managerOrganizationRepository;
    public void save(ManagerOrganization managerOrganization)
    {
        managerOrganizationRepository.save(managerOrganization);
    }
}
