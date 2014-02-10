package org.feuyeux.air.io.network.nio.udpcmd;

import java.io.Serializable;

/**
 * Created by Administrator on 14-2-10.
 */
public class UDPCommand implements Serializable {
    private int type;
    private String pair;

    public UDPCommand(int type, String pair) {
        this.type = type;
        this.pair = pair;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    @Override
    public String toString() {
        return type + "-" + pair;
    }
}
