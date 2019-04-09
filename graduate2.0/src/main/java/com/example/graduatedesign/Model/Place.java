package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Data
@Builder
@Entity(name="t_place")
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long placeId;
    @OneToMany(mappedBy = "place",cascade = CascadeType.ALL)
    private List<Activity> activityList;
    private String placeName;
    private int status;
    private Calendar createTime;
    private Calendar updateTime;
}
