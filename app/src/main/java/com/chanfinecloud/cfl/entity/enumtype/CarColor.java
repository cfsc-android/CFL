package com.chanfinecloud.cfl.entity.enumtype;

/**
 * Created by zengx on 2019/5/25.
 * Describe:
 */
public enum CarColor {
    WHITE("白色","WHITE"),
    SILVERY("银色","SILVERY"),
    GRAY("灰色","GRAY"),
    BLACK("黑色","BLACK"),
    RED("红色","RED"),
    NAVY_BLUE("深蓝色","NAVYBLUE"),
    BLUE("蓝色","BLUE"),
    YELLOW("黄色","YELLOW"),
    GREEN("绿色","GREEN"),
    BROWN("棕色","BROWN"),
    PINK("粉色","PINK"),
    VIOLET("紫色","PURPLE"),
    OTHER("其他颜色","OTHER");
    private final String color;
    private final String value;
    CarColor(String color,String value){
        this.color=color;
        this.value=value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }
}
