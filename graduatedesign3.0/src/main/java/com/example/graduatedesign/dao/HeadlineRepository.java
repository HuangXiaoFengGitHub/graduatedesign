package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Headline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeadlineRepository extends JpaRepository<Headline,Long> {

    List<Headline> findHeadlinesByStatusOrderByPriorityDesc(int status);
}
