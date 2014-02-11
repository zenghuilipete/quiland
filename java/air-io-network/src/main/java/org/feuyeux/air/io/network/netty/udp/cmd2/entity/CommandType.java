package org.feuyeux.air.io.network.netty.udp.cmd2.entity;

/**
 * Created by erichan on 2/10/14.
 */
public enum CommandType {
    KEY(0), Mouse(1);
    private int value;

    CommandType(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static CommandType getInstance(int controlType) {
        switch (controlType) {
            case 0:
                return CommandType.KEY;
            case 1:
                return CommandType.Mouse;
            default:
                return CommandType.KEY;
        }
    }
}