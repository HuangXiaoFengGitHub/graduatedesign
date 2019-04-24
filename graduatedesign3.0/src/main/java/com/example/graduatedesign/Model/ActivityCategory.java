package com.example.graduatedesign.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "t_activity_category")
public class ActivityCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityCategoryId;
    private String activityCategoryName;
    @Column(columnDefinition = "text")
    private String imgAddr;//图片地址
    private int priority;
    private long parentId;
    private Calendar createTime;
    private Calendar updateTime;
    @OneToMany(mappedBy = "category")
    private Set<Activity> activities=new HashSet<>();

}
