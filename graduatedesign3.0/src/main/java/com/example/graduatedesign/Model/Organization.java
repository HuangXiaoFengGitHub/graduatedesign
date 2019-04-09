package com.example.graduatedesign.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="t_organization")
@ToString(exclude = "likeUsers")
@EqualsAndHashCode(exclude = "likeUsers")
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long organizationId;
    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Activity> activities;
    private String organizationName;
    @Column(columnDefinition = "text")
    private String organizationDesc;
    private String organizationImg;
    private long parentId;
    private String wechatImg;
    private String wechatAccount;
    //private String wechatName;
    private long likeCount;
    private long commentCount;
    private String password;
    private long activityCount;
    private int grade; //0-100分
    private int priority; //0-10分
    private String email;
    private Calendar createTime;
    private Calendar updateTime;
    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL)
    private Set<ManagerOrganization> managerOrganizations;
    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL)
    private Set<ActivityArticle> activityArticles;
    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL)
    private Set<Attachment> attachments;
    //组织通知相关
    @OneToMany(mappedBy = "organization")
    private Set<OrganizationMessage> organizationMessages;
    @OneToMany(mappedBy = "sender")
    private Set<OrganizationMessage> organizationMessagesSender;
    //评论相关
    @OneToMany(mappedBy = "organization")
    private Set<OrganizationComment> organizationComments;
    @OneToMany(mappedBy = "organization")
    private Set<OrganizationScore> organizationScores;
    @ManyToMany(mappedBy ="likeOrganizations",fetch = FetchType.LAZY)
    //关注
    @JsonIgnoreProperties(value = { "likeOrganizations" })
    private Set<User>  likeUsers=new HashSet<>();
    //标签
    @ManyToMany
    @JoinTable(name="t_organization_tags",joinColumns = @JoinColumn(name="organization_id",referencedColumnName = "organizationId"),inverseJoinColumns = @JoinColumn(name="tag_id",referencedColumnName = "tagId"))
    private Set<Tags> tags=new HashSet<>();

}
