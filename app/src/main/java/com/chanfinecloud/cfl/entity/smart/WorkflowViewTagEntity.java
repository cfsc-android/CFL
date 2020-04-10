package com.chanfinecloud.cfl.entity.smart;

import com.chanfinecloud.cfl.entity.WorkflowViewEntity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/4/9.
 * Version: 1.0
 * Describe:
 */
public class WorkflowViewTagEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String formType;
    private String formKey;
    private WorkflowViewEntity workflowView;

    public WorkflowViewTagEntity(String formType, String formKey, WorkflowViewEntity workflowView) {
        this.formType = formType;
        this.formKey = formKey;
        this.workflowView = workflowView;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public WorkflowViewEntity getWorkflowView() {
        return workflowView;
    }

    public void setWorkflowView(WorkflowViewEntity workflowView) {
        this.workflowView = workflowView;
    }
}
