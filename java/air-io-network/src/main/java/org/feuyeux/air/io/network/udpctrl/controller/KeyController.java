package org.feuyeux.air.io.network.udpctrl.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.udpctrl.controlinfo.ControlInfo;
import org.feuyeux.air.io.network.udpctrl.controlinfo.KeyControlInfo;

/**
 * Created by erichan on 2/10/14.
 */
public class KeyController implements Controller {
    private final static Logger logger = LogManager.getLogger(KeyController.class);

    KeyController() {

    }

    @Override
    public void process(ControlInfo controlInfo) {
        KeyControlInfo keyControlInfo = (KeyControlInfo) controlInfo;
        logger.debug("KEY Press is:{}", keyControlInfo.getKeyPress());
    }
}
