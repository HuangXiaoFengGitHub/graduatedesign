package com.example.test1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="t_manager_organization")
public class ManagerOrganization implements Serializable {
    @Id
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user; //外键
    @Id
    @ManyToOne()
    @JoinColumn(name="organization_id")
    private Organization organization; //外键
    private int grade;//审核等级
    private Calendar createTime;
    private Calendar updateTime;
}
