package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Activity findByActivityId(Long id);
}
