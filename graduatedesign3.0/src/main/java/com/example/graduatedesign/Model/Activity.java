package com.example.graduatedesign.Model;


import com.example.graduatedesign.enums.ActivityState;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_activity")
@ToString
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityId;
    private String activityName;
    @Column(nullable = true)
    private int priority;
    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization; //外键
//    @Enumerated(EnumType.STRING)
//   private ActivityCategory category;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private ActivityCategory category;
    @Enumerated(EnumType.STRING)
    private ActivityState status;
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
    //标签
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="t_activity_tags",joinColumns = @JoinColumn(name="activity_id",referencedColumnName = "activityId"),inverseJoinColumns = @JoinColumn(name="tag_id",referencedColumnName = "tagId"))
    private Set<Tags> tags=new HashSet<>();
}
