package com.example.test1.demo.dao;

import com.example.test1.demo.Model.Academy;
import com.example.test1.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademyRepository extends JpaRepository<Academy,Long> {

}
