package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Academy;
import com.example.graduatedesign.Model.Account;
import com.example.graduatedesign.Model.User;

import java.util.List;

public interface AcademyServiceImp {
   Academy findAcademyByName(String name);
   List<Academy> findAll();

}
