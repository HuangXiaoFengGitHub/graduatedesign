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
@Entity(name = "t_organization_category")
public class OrganizationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long organizationCategoryId;
    private String organizationCategoryName;
    @Column(columnDefinition = "text")
    private String organizationDesc;
    @Column(columnDefinition = "text")
    private String imgAddr;//图片地址
    private int priority;
    private long parentId;
    private Calendar createTime;
    private Calendar updateTime;
    @OneToMany(mappedBy = "organizationCategory")
    private Set<Organization> organizations=new HashSet<>();
//    @OneToMany(mappedBy = "activityCategory")
//    private Set<Tags> tags=new HashSet<>();
}
