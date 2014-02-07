package org.feuyeux.mq.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;

public class TextListener implements MessageListener {
    Logger log= LogManager.getLogger();

    @Override
    public void onMessage(Message m) {
        System.out.println("==== TextListener onMessage ====");
        try {
            if (m instanceof TextMessage) {
                log.info("Reading message: " + m.getBody(String.class));
            } else {
                log.info("Message is not a TextMessage");
            }
        } catch (MessageFormatException e) {
            log.error("Message : " + m);
            log.error("Exception= " + e.toString());
        } catch (JMSException | JMSRuntimeException e) {
            log.error("Message : " + m);
            log.error("JMSException in onMessage(): " + e.toString());
        }
    }
}
