package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Academy;
import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.dao.AcademyRepository;
import com.example.graduatedesign.dao.AccountRepostory;
import com.example.graduatedesign.service.serviceImp.AcademyServiceImp;
import com.example.graduatedesign.service.serviceImp.AccountServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AcademyService implements AcademyServiceImp {
    @Autowired
    AcademyRepository academyRepository;
    public Academy findAcademyByName(String name)
    {
        return academyRepository.findByAcademyName(name);
    }
    public List<Academy> findAll()
    {
        return academyRepository.findAll();
    }
}
