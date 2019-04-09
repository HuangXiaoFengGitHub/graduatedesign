package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_headline")
public class Headline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long headlineId;
    private String headlineName;
    @Column(columnDefinition = "text")
    private String link;
    private int status;
    private int priority;
    @Column(columnDefinition = "text")
    private String imgAddr;
    private Calendar createTime;
    private Calendar updateTime;
}
