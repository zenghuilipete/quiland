package org.feuyeux.mq;

import org.feuyeux.mq.basic.Producer;

public class AirJMS2Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        switch (args[0]) {
            case "P":
                Producer.go(args[1], Integer.valueOf(args[2]));
                break;
        }
    }
}
