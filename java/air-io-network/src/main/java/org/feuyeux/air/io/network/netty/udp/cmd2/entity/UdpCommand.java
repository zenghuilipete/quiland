package org.feuyeux.air.io.network.netty.udp.cmd2.entity;

import org.feuyeux.air.io.network.netty.udp.cmd2.info.ControlInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 14-2-10.
 */
public class UdpCommand implements Serializable {
    private static final long serialVersionUID = 6349615867907081969L;
    private CommandType type;
    private ControlInfo controlInfo;

    public UdpCommand(CommandType type, ControlInfo controlInfo) {
        this.type = type;
        this.controlInfo = controlInfo;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public ControlInfo getControlInfo() {
        return controlInfo;
    }

    public void setControlInfo(ControlInfo controlInfo) {
        this.controlInfo = controlInfo;
    }

    @Override
    public String toString() {
        return type + "-" + controlInfo;
    }
}
