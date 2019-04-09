package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_activity_score")
public class ActivityScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long scoreId;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="activity_id")
    private Activity activity;
    private int score;
    private Calendar createTime;
    private Calendar updateTime;
}
