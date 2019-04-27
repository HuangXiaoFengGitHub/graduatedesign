package com.example.graduatedesign.Model;

import lombok.*;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.*;

@Entity(name="t_tags")
@Getter
@Setter
@ToString(exclude = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tagId;
    private int priority; //优先级
    private String tagName;
    private Calendar createTime;
    private Calendar updateTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_category_id")
    private ActivityCategory activityCategory;
    @ManyToMany(mappedBy = "tags")
    private Set<User> users=new HashSet<>();
    @ManyToMany(mappedBy = "tags")
    private Set<Organization> organizations=new HashSet<>();
    @OneToMany(mappedBy = "tags")
    private Set<Activity> activities=new HashSet<>();
}
