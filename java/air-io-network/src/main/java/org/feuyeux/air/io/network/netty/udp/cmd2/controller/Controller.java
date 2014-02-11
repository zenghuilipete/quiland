package org.feuyeux.air.io.network.netty.udp.cmd2.controller;

import org.feuyeux.air.io.network.netty.udp.cmd2.info.ControlInfo;

/**
 * Created by erichan on 2/10/14.
 */
public interface Controller {
    void process(ControlInfo controlInfo);
}
