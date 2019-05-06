package com.example.graduatedesign.util;

public class CommonUtil {
    public static String activityStateChange(int state)
    {
        switch(state){
            case 1 :
                return "审核中";
            case 2 :
                return "未通过审核";
            //你可以有任意数量的case语句
            case 3 :
                return "审核成功";
            case 4 :
                return "未开始";
            case 5 :
                return "正在进行";
            case 6 :
                return "已经结束";
            default : //可选
                return "不明状态";
        }
    }
    public static int activityStateChange(String state)
    {
        switch(state){
            case "审核中" :
                return 1;
            case "未通过审核" :
                return 2;
            //你可以有任意数量的case语句
            case "审核成功" :
                return 3;
            case "未开始" :
                return 4;
            case "正在进行" :
                return 5;
            case "已结束" :
                return 6;
            default : //可选
                return -1;
        }
    }
}
