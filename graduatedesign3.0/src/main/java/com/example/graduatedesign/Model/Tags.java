package com.example.graduatedesign.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity(name="t_tags")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tagId;
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
