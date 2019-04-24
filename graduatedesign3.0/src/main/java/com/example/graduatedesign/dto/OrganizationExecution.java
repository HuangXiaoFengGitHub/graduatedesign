package com.example.graduatedesign.dto;


import com.example.graduatedesign.Model.Organization;
import com.example.graduatedesign.enums.OrganizationState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationExecution {

    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 店铺数量
    private int count;

    // 操作的shop（增删改活动的时候用）
    private Organization organization;

    // 获取的活动列表(查询活动列表的时候用)
    private List<Organization> shopList;

    // 店铺操作失败的时候使用的构造器
    public OrganizationExecution(OrganizationState stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    // 店铺操作成功使用的构造器
    public OrganizationExecution(OrganizationState stateEnum, Organization shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.organization = shop;
    }

    // 店铺操作成功使用的构造器
    public OrganizationExecution(OrganizationState stateEnum, List<Organization> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }


}
