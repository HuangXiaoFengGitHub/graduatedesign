package com.example.graduatedesign.enums;

public enum OrganizationState {
    CHECK(0, "审核中"), FAILURE(-1, "非法组织"), SUCCESS(1, "操作成功"), PASS(2, "通过审核"),
    POST(3,"未开始"),GOING(4,"正在进行"),END(5,"已结束") ,INNER_ERROR(
            -1001, "操作失败"), NULL_ACTIVITYID(-1002, "OrganizationId为空"), NULL_ACTIVITY_INFO(
            -1003, "传入了空的页码信息"),NULL_AUTH_INFO(-1004,"密码信息为空");
    private int state;
    private String stateInfo;
    OrganizationState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OrganizationState stateOf(int index) {
        for (OrganizationState state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
