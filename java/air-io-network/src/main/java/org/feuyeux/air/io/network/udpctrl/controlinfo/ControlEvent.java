package org.feuyeux.air.io.network.udpctrl.controlinfo;

import java.io.Serializable;

/**
 * Created by erichan on 2/10/14.
 */
public class ControlEvent implements Serializable {
    private ControlType controlType;
    private ControlInfo controlInfo;

    public ControlEvent(ControlType controlType, ControlInfo controlInfo) {
        this.controlType = controlType;
        this.controlInfo = controlInfo;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    public ControlInfo getControlInfo() {
        return controlInfo;
    }

    public void setControlInfo(ControlInfo controlInfo) {
        this.controlInfo = controlInfo;
    }

    @Override
    public String toString() {
        return "ControlEvent[" + controlType + "," + controlInfo + "]";
    }
}
