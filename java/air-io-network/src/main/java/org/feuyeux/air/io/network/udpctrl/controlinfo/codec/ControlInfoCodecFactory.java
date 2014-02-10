package org.feuyeux.air.io.network.udpctrl.controlinfo.codec;

import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlType;
import org.feuyeux.air.io.network.udpctrl.controller.Controller;
import org.feuyeux.air.io.network.udpctrl.controller.KeyController;
import org.feuyeux.air.io.network.udpctrl.controller.MouseController;

/**
 * Created by erichan on 2/10/14.
 */
public class ControlInfoCodecFactory {
    public static ControlInfoCodec getInstance(ControlType controlType) {
        switch (controlType) {
            case KEY: {
                return new KeyControlInfoCodec();
            }
            default: {
                return null;
            }
        }
    }
}
