package org.feuyeux.mq.basic;

import javax.jms.*;

public class TextListener implements MessageListener {

    @Override
    public void onMessage(Message m) {
        System.out.println("==== TextListener onMessage ====");
        try {
            if (m instanceof TextMessage) {
                System.out.println("Reading message: " + m.getBody(String.class));
            } else {
                System.out.println("Message is not a TextMessage");
            }
        } catch (MessageFormatException e) {
            System.err.println("Message : " + m);
            System.err.println("Exception= " + e.toString());
        } catch (JMSException | JMSRuntimeException e) {
            System.err.println("Message : " + m);
            System.err.println("JMSException in onMessage(): " + e.toString());
        }
    }
}
