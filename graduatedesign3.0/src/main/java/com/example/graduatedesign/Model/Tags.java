package com.example.graduatedesign.Model;

import lombok.*;

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
    @ManyToMany(mappedBy = "tags")
    private Set<User> users=new HashSet<>();
    @ManyToMany(mappedBy = "tags")
    private Set<Organization> organizations=new HashSet<>();
    @ManyToMany(mappedBy = "tags")
    private Set<Activity> activities=new HashSet<>();
}
