package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private List<User> users=new ArrayList<>();
    @ManyToMany(mappedBy = "tags")
    private List<Organization> organizations=new ArrayList<>();
}
