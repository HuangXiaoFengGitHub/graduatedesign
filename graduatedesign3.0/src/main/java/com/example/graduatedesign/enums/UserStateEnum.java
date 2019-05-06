package com.example.graduatedesign.enums;

import lombok.Getter;

@Getter
public enum UserStateEnum {
    LOGINFAIL(-1, "密码或帐号输入有误"), SUCCESS(1, "操作成功"), NULL_AUTH_INFO(-1006,
            "信息为空"),REPEAT(-0005,"新旧密码不能相同"), ONLY_ONE_ACCOUNT(-1007,"最多只能绑定一个本地帐号");

    private int state;
    private String stateInfo;
    private UserStateEnum(int state,String stateInfo)
    {
        this.state=state;
        this.stateInfo=stateInfo;
    }
    public UserStateEnum stateof(int index)
    {
        for(UserStateEnum stateEnum:values())
        {
            if(stateEnum.getState()==index)
                return stateEnum;
        }
        return null;
    }

}
