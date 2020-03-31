package com.chanfinecloud.cfl.entity.enumtype;

/**
 * Created by zengx on 2019/5/25.
 * Describe:
 */
public enum PlateColor {
    BLUE("蓝色","BLUE"),
    YELLOW("黄色","YELLOW"),
    WHITE("白色","WHITE"),
    BLACK("黑色","BLACK"),
    GREEN("绿色","GREEN"),
    AVIATION("民航黑色","C_BLACK"),
    OTHER("其他颜色","OTHER");
    private final String color;
    private final String value;

    PlateColor(String color,String value) {
        this.color = color;
        this.value=value;
    }

    public String getColor() {
        return color;
    }

    public String getValue(){
        return value;
    }
}
