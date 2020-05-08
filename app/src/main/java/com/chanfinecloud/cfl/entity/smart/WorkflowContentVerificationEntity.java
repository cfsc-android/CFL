package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Damien -- 工单流程内容格式校验
 */
public class WorkflowContentVerificationEntity implements Serializable {

    private String type;
    private String min;
    private String max;
    private String message;
    private String minLength;
    private String maxLength;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String toString() {
        return "WorkflowContentVerificationEntity{" +
                "type='" + type + '\'' +
                ", min='" + min + '\'' +
                ", max='" + max + '\'' +
                ", message='" + message + '\'' +
                ", minLength='" + minLength + '\'' +
                ", maxLength='" + maxLength + '\'' +
                '}';
    }
}
