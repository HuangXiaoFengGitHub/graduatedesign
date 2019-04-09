package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name="t_activity_article")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long articleId;
    private String title;
    @Column(columnDefinition = "text")
    private String articleDesc;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
    private String author;
    private int ontop;
    private int deleted;
    private String htmlPath;
    private String filePath;
    private int articleStatus;
    private long commentCount;
    private long hits;
    @Column(columnDefinition = "longtext")
    private String content;
    private String thumbAddr;
    private String keyWord;
    private int priority;
    private String htmlUrl;
    @OneToOne(mappedBy = "activityMainArticle")
    private Activity mainActivity;
    private Calendar createTime;
    private Calendar updateTime;

}
