package com.example.test1.demo.Model;

import com.example.test1.demo.enums.ActivityCategory;
import com.example.test1.demo.enums.ActivityStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityId;
    private String activityName;
    private int priority;
    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization; //外键
    @Enumerated(EnumType.STRING)
    private ActivityCategory category;
    @Enumerated(EnumType.STRING)
    private ActivityStateEnum status;
    @Column(columnDefinition = "longtext")
    private String articleDesc;
    @ManyToOne
    @JoinColumn(name="place_id")
    private Place place;              //外键
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="main_article_id")
    private  ActivityArticle activityMainArticle; //外键
    private Calendar startTime;
    private Calendar endTime;
    private Calendar createTime;
    private Calendar updateTime;
    @Column(columnDefinition="text")
    private String imgAddr;
    @OneToMany(mappedBy = "activity")
    private List<ActivityImg> activityImgs;
    @OneToMany(mappedBy = "activity")
    private List<ActivityArticle> activityArticles;
    @OneToMany(mappedBy = "activity")
    private List<Attachment> attachments;
    @OneToMany(mappedBy = "activity")
    private List<ActivityComment> activityComments;
    @OneToMany(mappedBy = "activity")
    private List<ActivityScore> activityScores;
    //报名关注
    @ManyToMany(mappedBy = "activities",cascade = CascadeType.ALL)
    private List<User> users=new ArrayList<>();
    @ManyToMany(mappedBy = "likeActivities",cascade = CascadeType.ALL)
    private List<User> likeUsers=new ArrayList<>();
}
