package com.example.test1.demo.enums;

public enum ActivityStateEnum {

    CHECK(0, "审核中"), FAILURE(-1, "非法活动"), SUCCESS(1, "操作成功"), PASS(2, "通过审核"),
    POST(3,"已发布"),GOING(4,"正在进行"),END(5,"已结束") ,INNER_ERROR(
            -1001, "操作失败"), NULL_ACTIVITYID(-1002, "AactivtyId为空"), NULL_ACTIVITY_INFO(
            -1003, "传入了空的信息");

    private int state;

    private String stateInfo;

    ActivityStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ActivityStateEnum stateOf(int index) {
        for (ActivityStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
