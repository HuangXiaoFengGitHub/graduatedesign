package com.example.test1.demo.dao;

import com.example.test1.demo.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Activity findByActivityId(Long id);
}
