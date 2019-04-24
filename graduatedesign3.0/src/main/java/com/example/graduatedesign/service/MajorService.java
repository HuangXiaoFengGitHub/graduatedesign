package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Academy;
import com.example.graduatedesign.Model.Major;
import com.example.graduatedesign.dao.AcademyRepository;
import com.example.graduatedesign.dao.MajorRepository;
import com.example.graduatedesign.service.serviceImp.AcademyServiceImp;
import com.example.graduatedesign.service.serviceImp.MajorServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MajorService implements MajorServiceImp {
    @Autowired
    MajorRepository majorRepository;
    public Major   findMajorByName(String name){
        return majorRepository.findByMajorName(name);
    }
      public List<Major> findAll()
    {
        return majorRepository.findAll();
    }
}
