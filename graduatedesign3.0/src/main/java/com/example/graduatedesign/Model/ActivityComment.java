package com.example.graduatedesign.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Data
@Builder
@Entity(name="t_activity_comment")
@AllArgsConstructor
@NoArgsConstructor
public class ActivityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentId;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="activity_id")
    private Activity activity;
    @Column(columnDefinition = "longtext")
    private String content;
    private long parentId;
    private int commentLikeCount;
    private Calendar createTime;
    private Calendar updateTime;
}
