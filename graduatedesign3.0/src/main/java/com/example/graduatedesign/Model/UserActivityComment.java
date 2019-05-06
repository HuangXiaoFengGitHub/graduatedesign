package com.example.graduatedesign.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name = "t_user_activity_comment")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "comment_id")
    private ActivityComment activityComment;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="activity_id")
    private Activity activity;
    private Calendar createTime;
    private Calendar updateTime;
}
