package com.example.graduatedesign.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Calendar;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long noticeId;
    private String title;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
    @Column(columnDefinition = "longtext")
    private String content;
    @Column(nullable = true)
    private int readCount;
    private Calendar createTime;
    private Calendar updateTime;
}
