package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyRepository extends JpaRepository<Academy,Long> {
    Academy findByAcademyName(String name);
}
