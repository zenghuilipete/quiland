package org.feuyeux.mq.basic;

import javax.jms.*;

public class TextListener implements MessageListener {

    @Override
    public void onMessage(Message m) {
        try {
            if (m instanceof TextMessage) {
                System.out.println("Reading message: " + m.getBody(String.class));
            } else {
                System.out.println("Message is not a TextMessage");
            }
        } catch (JMSException | JMSRuntimeException e) {
            System.err.println("JMSException in onMessage(): " + e.toString());
        }
    }
}
