package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/3/14.
 * Version: 1.0
 * Describe:
 */
public class WorkflowFormEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * formId : 1001
     * formName : 客服中心确认工单
     * formContent:[WorkflowFormContentEntity]
     */

    private int formId;
    private String formName;
    private List<WorkflowFormContentEntity> formContent;

    public List<WorkflowFormContentEntity> getFormContent() {
        return formContent;
    }

    public void setFormContent(List<WorkflowFormContentEntity> formContent) {
        this.formContent = formContent;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
