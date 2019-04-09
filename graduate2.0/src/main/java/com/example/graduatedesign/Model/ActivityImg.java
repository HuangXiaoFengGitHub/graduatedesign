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
@Entity(name = "t_activity_img")
public class ActivityImg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imageId;
    @Column(columnDefinition = "text")
    private String imageAddr;
    @Column(columnDefinition = "text")
    private String imageDesc;
    private int priority;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_id")
    private Activity activity;
    private Calendar createTime;
}
