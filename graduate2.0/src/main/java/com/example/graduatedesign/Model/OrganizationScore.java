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
@Entity(name = "t_organization_score")
public class OrganizationScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long scoreId;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization;
    private int score;
    private Calendar createTime;
    private Calendar updateTime;
}
