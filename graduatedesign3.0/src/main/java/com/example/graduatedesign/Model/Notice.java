package com.example.graduatedesign.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity(name = "t_notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long noticeId;
    private String noticeName;
}
