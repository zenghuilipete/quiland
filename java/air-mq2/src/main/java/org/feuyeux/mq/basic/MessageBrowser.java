package org.feuyeux.mq.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.mq.AirJMS2Env;
import org.hornetq.jms.server.embedded.EmbeddedJMS;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Enumeration;

public class MessageBrowser {
    Logger log= LogManager.getLogger();

    @Resource(lookup = AirJMS2Env.AIR_JMS_CF)
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = AirJMS2Env.AIR_QUEUE)
    private static Queue queue;

    public MessageBrowser(EmbeddedJMS jmsServer) throws Exception {
        connectionFactory = (ConnectionFactory) jmsServer.lookup(AirJMS2Env.AIR_JMS_CF);
        queue = (Queue) jmsServer.lookup(AirJMS2Env.AIR_QUEUE);
    }

    public void go() {
        log.info("==== MessageBrowser go ====");
        QueueBrowser browser;
        try (JMSContext context = connectionFactory.createContext();) {
            browser = context.createBrowser(queue);
            Enumeration msgs = browser.getEnumeration();

            if (!msgs.hasMoreElements()) {
                log.info("No messages in queue");
            } else {
                while (msgs.hasMoreElements()) {
                    Message tempMsg = (Message) msgs.nextElement();
                    log.info("Message: " + tempMsg.getJMSMessageID() + " : " + tempMsg.getBody(String.class));
                }
            }
        } catch (JMSException e) {
            log.error("Exception occurred: " + e.toString());
        }
    }
}
