package com.example.graduatedesign.demo.dao;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.dao.ActivityCategoryRepository;
import com.example.graduatedesign.dao.TagsRepository;
import com.example.graduatedesign.demo.BaseTest;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class TagsRepositoryTest extends BaseTest {
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    ActivityCategoryRepository activityCategoryRepository;
    public void test()
    {
       // ActivityCategory activityCategory=activityCategoryRepository.findByActivityCategoryId(2L);
       ActivityCategory activityCategory= ActivityCategory.builder().activityCategoryId(4L).build();
        List<Tags> tagsList=tagsRepository.findTagsByActivityCategory(activityCategory);
        log.info(tagsList.toString());
        Assert.assertTrue(tagsList.size()==2);
    }
}
