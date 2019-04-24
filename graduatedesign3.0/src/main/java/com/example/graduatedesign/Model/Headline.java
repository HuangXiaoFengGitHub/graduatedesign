package com.example.graduatedesign.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

//头条轮播图
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
    private String link; //连接的URL
    private int status;
    private int priority;
    @Column(columnDefinition = "text")
    private String imgAddr; //对应的图片
    private Calendar createTime;
    private Calendar updateTime;
}
