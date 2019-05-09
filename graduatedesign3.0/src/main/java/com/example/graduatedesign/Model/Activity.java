package com.example.graduatedesign.Model;


import com.example.graduatedesign.enums.ActivityState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_activity")
//@ToString(exclude = {"likeUsers","users"})
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityId;
    private String activityName;
    private int enableStatus;//1,通过审核且可以发布的活动，0.不能在前台显示的活动
    @Column(nullable = true)
    private int priority;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="organization_id")
    private Organization organization; //外键
//    @Enumerated(EnumType.STRING)
//   private ActivityCategory category;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private ActivityCategory category;
   // @Enumerated(EnumType.STRING)
    private int status; //1.审核中，2.未通过审核 3.审核通过 4.未开始 5.正在进行 6.已结束
    @Column(columnDefinition = "longtext")
    private String activityDesc; //活动简介
    @ManyToOne
    @JoinColumn(name="place_id")
    private Place place;              //外键
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="main_article_id")
    private  ActivityArticle activityMainArticle; //外键
    private long minPrice; //活动经费下限
    private long maxPrice; //活动经费上限
    @Column(columnDefinition = "text")
    private String checkComment;
    private Calendar startTime;
    private Calendar endTime;
    private Calendar createTime;
    private Calendar updateTime;
    @Column(columnDefinition="text")
    private String imgAddr;
    @OneToMany(mappedBy = "activity")
    private Set<Notice> notices;
    @OneToMany(mappedBy = "activity")
    private List<ActivityImg> activityImgs;
    @OneToMany(mappedBy = "activity")
    private List<ActivityArticle> activityArticles;
    @OneToMany(mappedBy = "activity")
    private List<Attachment> attachments;

//    @OneToMany(mappedBy = "activity")
//    private List<ActivityComment> activityComments;
    @OneToMany(mappedBy = "activity")
    private List<ActivityScore> activityScores;
    //评论
    @JsonIgnore
    @OneToMany(mappedBy = "activity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserActivityComment> userActivityComments;
    //报名关注
    @ManyToMany(mappedBy = "signUpActivities",cascade = CascadeType.ALL)
    private List<User> users=new ArrayList<>();
    @ManyToMany(mappedBy = "likeActivities",cascade = CascadeType.ALL)
    private List<User> likeUsers=new ArrayList<>();
    //标签
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    private Tags tags;
    //@JoinTable(name="t_activity_tags",joinColumns = @JoinColumn(name="activity_id",referencedColumnName = "activityId"),inverseJoinColumns = @JoinColumn(name="tag_id",referencedColumnName = "tagId"))
    //private Set<Tags> tags=new HashSet<>();
}
