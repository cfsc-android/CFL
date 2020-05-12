package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/3/14.
 * Version: 1.0
 * Describe:
 */
public class WorkflowFormContentEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * formItemType : choose
     * formItemLabel : 单选
     * sort : 1
     */

    private String formItemType;
    private String formKey;
    private String formItemLabel;
    private String fieldName;
    private int sort;
    private String required;
    private List<WorkflowContentVerificationEntity> valid;


    public String getFormItemType() {
        return formItemType;
    }

    public void setFormItemType(String formItemType) {
        this.formItemType = formItemType;
    }

    public String getFormItemLabel() {
        return formItemLabel;
    }

    public void setFormItemLabel(String formItemLabel) {
        this.formItemLabel = formItemLabel;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public List<WorkflowContentVerificationEntity> getValid() {
        return valid;
    }

    public void setValid(List<WorkflowContentVerificationEntity> valid) {
        this.valid = valid;
    }
}
