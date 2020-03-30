package com.chanfinecloud.cfl.entity.smart;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 公告类型
 */
public enum NoticeType {
    工程进度("16f0e1e6d35e4705598d03b49a4b39a1"),
    社区公告("16f0e1e6d35e4705598d03b49a4b39b4"),
    轮播动态("16f0e1e6d35e4705598d03b49a4b39h2"),
    热点关注("16f5aea04976f570db362344f46bce24"),
    入伙("1707a68b6d245b87c6d91d6448da56b3");
    private final String type;

    NoticeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static String getName(String type){
        String name="";
        NoticeType[] types = NoticeType.values();
        for (int i = 0; i < types.length; i++) {
            if(type.equals(types[i].type)){
                name=types[i].name();
            }
        }
        return name;
    }
}