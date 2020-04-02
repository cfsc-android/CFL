package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/27.
 * Version: 1.0
 * Describe:
 */
public class EquipmentInfoBo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * deviceSerial : 231857475
     * devicePlatformId : 85e234e64ede4e3a92c37f408e54a383
     * deviceName : 长房时代城二期8栋3单元
     */

    private String deviceSerial;
    private String devicePlatformId;
    private String deviceName;

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getDevicePlatformId() {
        return devicePlatformId;
    }

    public void setDevicePlatformId(String devicePlatformId) {
        this.devicePlatformId = devicePlatformId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
