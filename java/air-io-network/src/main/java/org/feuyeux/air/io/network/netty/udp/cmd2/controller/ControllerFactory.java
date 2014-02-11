package org.feuyeux.air.io.network.netty.udp.cmd2.controller;

import org.feuyeux.air.io.network.netty.udp.cmd2.entity.CommandType;

/**
 * Created by erichan on 2/10/14.
 */
public class ControllerFactory {
    public static Controller getInstance(CommandType controlType) {
        switch (controlType) {
            case KEY: {
                return new KeyController();
            }
            default: {
                return null;
            }
        }
    }
}
