package com.example.graduatedesign.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_activity_category")
public class ActivityCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityCategoryId;
    private String activityCategoryName;
    @Column(columnDefinition = "text")
    private String activityDesc;
    @Column(columnDefinition = "text")
    private String imgAddr;//图片地址
    private int priority;
    private long parentId;
    private Calendar createTime;
    private Calendar updateTime;
    @OneToMany(mappedBy = "category")
    private Set<Activity> activities=new HashSet<>();
    @OneToMany(mappedBy = "activityCategory")
    private Set<Tags> tags=new HashSet<>();
}
