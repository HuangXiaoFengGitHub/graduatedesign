package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory,Long> {
    ActivityCategory findByActivityCategoryId(long id);
    ActivityCategory findByActivityCategoryName(String name);
}
