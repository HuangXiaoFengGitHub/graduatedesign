package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Data
@Builder
@Entity(name="t_organization_comment")
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentId;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="activity_id")
    private Organization organization;
    @Column(columnDefinition = "longtext")
    private String content;
    private long parentId;
    private int commentLikeCount;
    private Calendar createTime;
    private Calendar updateTime;
}
