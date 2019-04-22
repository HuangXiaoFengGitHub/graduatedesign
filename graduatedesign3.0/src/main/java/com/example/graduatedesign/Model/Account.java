package com.example.graduatedesign.Model;

import com.example.graduatedesign.enums.IdentifyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name="t_account")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    @Enumerated(EnumType.STRING)
    private IdentifyType identifyType;
    private long password;
    @OneToOne(mappedBy = "account")
    private User user;
    private Calendar createTime;
    private Calendar updateTime;
}
