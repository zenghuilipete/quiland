package org.feuyeux.air.io.network.udpctrl.controlinfo;

/**
 * Created by erichan on 2/10/14.
 */
public enum ControlType {
    KEY(0), Mouse(1);
    private int value;

    ControlType(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ControlType getInstance(int controlType) {
        switch (controlType) {
            case 0:
                return ControlType.KEY;
            case 1:
                return ControlType.Mouse;
            default:
                return ControlType.KEY;
        }
    }
}