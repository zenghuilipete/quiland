package org.feuyeux.air.io.network.udpctrl.controlinfo;

/**
 * Created by erichan on 2/10/14.
 */
public class KeyControlInfo implements ControlInfo {
    private String keyPress;

    public KeyControlInfo() {
    }

    public KeyControlInfo(String keyPress) {
        this.keyPress = keyPress;
    }

    public String getKeyPress() {
        return keyPress;
    }

    public void setKeyPress(String keyPress) {
        this.keyPress = keyPress;
    }

    @Override
    public String toString() {
        return getKeyPress();
    }
}
