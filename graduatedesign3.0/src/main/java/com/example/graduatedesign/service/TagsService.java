package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.dao.ActivityCategoryRepository;
import com.example.graduatedesign.dao.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {
    @Autowired
    ActivityCategoryRepository activityCategoryRepository;
    @Autowired
    TagsRepository tagsRepository;
    public Tags findTagsById(long id)
    {
        return tagsRepository.findByTagId(id);
    }
    public Tags findTagsByName(String name){
        return tagsRepository.findByTagName(name);
    }
    public List<Tags> findAll()
    {
        return tagsRepository.findAll();
    }
    public List<Tags> findByCategory(long activityCategoryId)
    {
        ActivityCategory activityCategory=activityCategoryRepository.findByActivityCategoryId(activityCategoryId);
        if(activityCategory!=null)
        {
            return tagsRepository.findTagsByActivityCategory(activityCategory);
        }
        else {
            return null;
        }
    }
}
