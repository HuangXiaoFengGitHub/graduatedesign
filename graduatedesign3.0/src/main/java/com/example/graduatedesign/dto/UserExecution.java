package com.example.graduatedesign.dto;

import com.example.graduatedesign.Model.User;
import com.example.graduatedesign.enums.UserStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExecution {
    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    private int count;

    private User user;

    private List<User> userList;

    // 失败的构造器
    public UserExecution(UserStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public UserExecution(UserStateEnum stateEnum,User user) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.user = user;
    }

    // 成功的构造器
    public UserExecution(UserStateEnum stateEnum,
                              List<User> userList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userList = userList;
    }
}
