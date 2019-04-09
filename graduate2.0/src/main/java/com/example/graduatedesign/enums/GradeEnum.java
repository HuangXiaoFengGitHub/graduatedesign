package com.example.test1.demo.enums;

import lombok.Getter;

@Getter
public enum  GradeEnum {
    ONE(1,"学生管理员"),TWO(2,"一级老师管理员"),THREE(3,"二级老师管理员"),CLASS(-1,"课室管理员");
    private int state;
    private String stateinfo;
    private GradeEnum(int state,String stateinfo)
    {
        this.state=state;
        this.stateinfo=stateinfo;
    }
}
