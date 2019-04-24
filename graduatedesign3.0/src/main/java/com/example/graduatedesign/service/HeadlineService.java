package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.Headline;
import com.example.graduatedesign.dao.HeadlineRepository;
import com.example.graduatedesign.service.serviceImp.HeadlineServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HeadlineService implements HeadlineServiceImp {
    @Autowired
    HeadlineRepository headlineRepository;
    public List<Headline> findAll()
    {
      return headlineRepository.findAll();
    }
    public List<Headline> findEnableHeadline(int status)
    {
        return headlineRepository.findHeadlinesByStatusOrderByPriorityDesc(status);
    }
}
