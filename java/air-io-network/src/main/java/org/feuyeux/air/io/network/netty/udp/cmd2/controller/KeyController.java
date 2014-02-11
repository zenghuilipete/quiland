package org.feuyeux.air.io.network.netty.udp.cmd2.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.ControlInfo;
import org.feuyeux.air.io.network.netty.udp.cmd2.info.KeyControlInfo;

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
        //......
    }
}
