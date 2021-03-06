package com.example.graduatedesign.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MAN(1,"男"),WOMAN(2,"女");
    private int state;
    private String stateInfo;
    private GenderEnum(int state,String stateInfo)
    {
        this.state=state;
        this.stateInfo=stateInfo;
    }
}
