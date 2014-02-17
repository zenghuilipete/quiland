package org.feuyeux.air.io.network.netty.udp.cmd2.entity;

/**
 * Created by erichan on 2/10/14.
 */
public enum CommandType {
    KEY(0), Mouse(1), Unknown(-1);
    private final int value;

    CommandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CommandType getInstance(int controlType) {
        switch (controlType) {
            case 0:
                return KEY;
            case 1:
                return Mouse;
            default:
                return Unknown;
        }
    }
}