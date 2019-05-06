package com.example.graduatedesign.Model;
import com.example.graduatedesign.Model.mongo.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.util.*;
@Builder
@Entity(name="t_user")
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "likeOrganizations")
@EqualsAndHashCode(exclude = "likeOrganizations")
@Getter
@Setter
@ToString
public class User {
    //IdClass(User.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long userId;
    private String nickName;
    private String userName;
    @Column(columnDefinition = "text")
    private String profile;//头像地址
    private String gender;
    private String grade;
    private String studentNumber;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "major_id")
    private Major major;
    private String phone;
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "academy_id")
    private Academy academy;
    @Column(columnDefinition = "text")
    private String userDesc;
    private long likeCount;
    private long joinCount;
    private Calendar createTime;
    private Calendar updateTime;
    private int isManager;
    private int isBan;
    private int isStudent;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account_id")
    private Account account;
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch = FetchType.EAGER)//管理关系
    private List<ManagerOrganization> managerOrganizations;
   // 用户私信相关
    @OneToMany(mappedBy ="user" ,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<PrivateMessage> privateMessages;
    @OneToMany(mappedBy ="friend",cascade = CascadeType.ALL,fetch=FetchType.EAGER )
    private Set<PrivateMessage> privateMessages2;
    @OneToMany(mappedBy ="sender" ,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<PrivateMessage> privateMessages3;
    @OneToMany(mappedBy ="receiver",cascade = CascadeType.ALL,fetch=FetchType.EAGER )
    private Set<PrivateMessage> privateMessages4;
    //组织私信相关
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<OrganizationMessage> organizationMessages;
    @OneToMany(mappedBy = "receiver",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<OrganizationMessage> organizationMessagesReceiver;
   //评论相关
//    @OneToMany(mappedBy ="user",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
//    private Set<ActivityComment> comments;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserActivityComment> userActivityComments;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<ActivityScore> activityScores;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<OrganizationScore> organizationScores;
    //报名关注相关
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="t_signUp",joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "activityId"))
    private Set<Activity> signUpActivities=new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "t_like_Activity",joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "activityId"))
    private Set<Activity> likeActivities=new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "t_like_comment",joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "commentId"))
    private Set<ActivityComment> likeComments=new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "likeUsers" })
    @JoinTable(name="t_like_organization",joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "organization_id", referencedColumnName = "organizationId"))
    private Set<Organization> likeOrganizations=new HashSet<>();
    //标签
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="t_user_tags",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "userId"),inverseJoinColumns = @JoinColumn(name="tag_id",referencedColumnName = "tagId"))
    private Set<Tags> tags=new HashSet<>();
}
