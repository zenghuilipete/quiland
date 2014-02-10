package org.feuyeux.air.io.network.udpctrl.controller;

import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlInfo;

/**
 * Created by erichan on 2/10/14.
 */
public interface Controller {
    void process(ControlInfo controlInfo);
}
