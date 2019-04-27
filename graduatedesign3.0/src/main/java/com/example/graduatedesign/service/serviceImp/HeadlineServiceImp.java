package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Headline;

import java.util.List;

public interface HeadlineServiceImp {
    List<Headline> findAll();
    public List<Headline> findHeadlineByStatus(int status);
}
