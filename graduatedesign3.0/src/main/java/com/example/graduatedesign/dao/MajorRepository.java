package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Academy;
import com.example.graduatedesign.Model.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major,Long> {
    Major findByMajorName(String name);

}
