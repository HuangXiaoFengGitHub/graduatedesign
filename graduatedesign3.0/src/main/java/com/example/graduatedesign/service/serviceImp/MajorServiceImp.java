package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.Major;

import java.util.List;

public interface MajorServiceImp {
    Major findMajorByName(String name);

    List<Major> findAll();
}
