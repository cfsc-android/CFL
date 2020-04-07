package com.chanfinecloud.cfl.entity.eventbus;

/**
 * Created by zengx on 2019/5/5.
 * Describe:
 */
public class EventBusMessage<T> {
    private String message;
    private T data;

    public EventBusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventBusMessage{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
