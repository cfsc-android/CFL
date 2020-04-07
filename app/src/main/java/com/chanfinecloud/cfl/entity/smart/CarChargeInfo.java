package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengx on 2019/6/14.
 * Describe:
 */
public class CarChargeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String plateNo;
    private String cardNo;
    private String personName;
    private String groupName;
    private List<ValidityInfo> validity;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ValidityInfo> getValidity() {
        return validity;
    }

    public void setValidity(List<ValidityInfo> validity) {
        this.validity = validity;
    }

    public class ValidityInfo{
        private String parkSyscode;
        private String parkName;
        private List<FunctionTimeInfo> functionTime;

        public String getParkSyscode() {
            return parkSyscode;
        }

        public void setParkSyscode(String parkSyscode) {
            this.parkSyscode = parkSyscode;
        }

        public String getParkName() {
            return parkName;
        }

        public void setParkName(String parkName) {
            this.parkName = parkName;
        }

        public List<FunctionTimeInfo> getFunctionTime() {
            return functionTime;
        }

        public void setFunctionTime(List<FunctionTimeInfo> functionTime) {
            this.functionTime = functionTime;
        }
    }

    public class FunctionTimeInfo{
        private String startTime;
        private String endTime;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
