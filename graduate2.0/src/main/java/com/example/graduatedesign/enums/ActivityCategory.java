package com.example.test1.demo.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ActivityCategory {

    COMPETITION(1,"比赛"),LECTURE(2,"讲座"),VOLUNTEER(3,"志愿服务"),HOBBY(4,"兴趣爱好");
    private int state;
    private String stateinfo;

    private ActivityCategory(int state,String stateinfo)
    {
        this.state=state;
        this.stateinfo=stateinfo;
    }


}
