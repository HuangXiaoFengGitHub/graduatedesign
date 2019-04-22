package com.example.graduatedesign.dto;


import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.enums.ActivityState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityExecution {

    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 店铺数量
    private int count;

    // 操作的shop（增删改活动的时候用）
    private Activity activity;

    // 获取的活动列表(查询活动列表的时候用)
    private List<Activity> shopList;

    // 店铺操作失败的时候使用的构造器
    public ActivityExecution(ActivityState stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    // 店铺操作成功使用的构造器
    public ActivityExecution(ActivityState stateEnum, Activity shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.activity = shop;
    }

    // 店铺操作成功使用的构造器
    public ActivityExecution(ActivityState stateEnum, List<Activity> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }


}
