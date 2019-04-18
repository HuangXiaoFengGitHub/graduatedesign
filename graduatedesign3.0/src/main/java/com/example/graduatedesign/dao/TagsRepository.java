package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.Model.Tags;
import com.example.graduatedesign.enums.ActivityState;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.HTML;
import java.util.Calendar;
import java.util.List;

public interface TagsRepository extends JpaRepository<Tags,Long> {
    //分页查询活动，可输入的条件有：活动类别，活动标签，活动状态，组织名称，活动时间等等
    Tags findByTagName(String name);
    Tags findByTagId(long id);
}
