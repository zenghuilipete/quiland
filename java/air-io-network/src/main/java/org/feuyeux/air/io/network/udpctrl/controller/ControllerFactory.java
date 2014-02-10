package org.feuyeux.air.io.network.udpctrl.controller;

import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlType;

/**
 * Created by erichan on 2/10/14.
 */
public class ControllerFactory {
    public static Controller getInstance(ControlType controlType) {
        switch (controlType) {
            case KEY: {
                return new KeyController();
            }
            case Mouse: {
                return new MouseController();
            }
            default: {
                return null;
            }
        }
    }
}
