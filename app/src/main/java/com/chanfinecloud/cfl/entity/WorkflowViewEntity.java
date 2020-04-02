package com.chanfinecloud.cfl.entity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/10.
 * Version: 1.0
 * Describe:
 */
public class WorkflowViewEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public WorkflowViewEntity(View view) {
        this.view = view;
    }

    private View view;
    private TextView label;
    private T content;
    private Button submit;
    private Button reject;
    private Button accept;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }

    public Button getReject() {
        return reject;
    }

    public void setReject(Button reject) {
        this.reject = reject;
    }

    public Button getAccept() {
        return accept;
    }

    public void setAccept(Button accept) {
        this.accept = accept;
    }
}
