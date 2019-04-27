package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Tags;

import java.util.List;

public interface TagsServiceImp {
    Tags findTagsById(long id);
    Tags findTagsByName(String name);
    public List<Tags> findByCategory(long activityCategoryId);
}
