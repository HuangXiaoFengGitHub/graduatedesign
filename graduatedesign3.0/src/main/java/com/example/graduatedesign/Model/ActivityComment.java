package com.example.graduatedesign.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity(name="t_activity_comment")
@AllArgsConstructor
@NoArgsConstructor
public class ActivityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentId;
    @Column(columnDefinition = "longtext")
    private String content;
    @Column(nullable = true)
    private long parentId;
    private int commentLikeCount;
    private Calendar createTime;
    private Calendar updateTime;
    @ManyToMany(mappedBy = "likeComments")
    @JsonIgnoreProperties(value = { "likeComments" })
    private Set<User> likeUsers=new HashSet<>();
    @JsonIgnore
    @OneToOne(mappedBy = "activityComment")
    private UserActivityComment userActivityComment;
}
