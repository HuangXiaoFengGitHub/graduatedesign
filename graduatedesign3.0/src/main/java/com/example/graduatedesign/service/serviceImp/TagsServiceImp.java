package com.example.graduatedesign.service.serviceImp;

import com.example.graduatedesign.Model.Tags;

public interface TagsServiceImp {
    Tags findTagsById(long id);
    Tags findTagsByName(String name);
}
