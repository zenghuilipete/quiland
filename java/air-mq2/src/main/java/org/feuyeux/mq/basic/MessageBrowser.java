package org.feuyeux.mq.basic;

import org.feuyeux.mq.AirJMS2Env;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Enumeration;

public class MessageBrowser {
    @Resource(lookup = AirJMS2Env.AIR_JMS_CF)
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = AirJMS2Env.AIR_QUEUE)
    private static Queue queue;

    public static void go(String... args) {
        QueueBrowser browser;

        /*
         * In a try-with-resources block, create context.
         * Create QueueBrowser.
         * Check for messages on queue.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            browser = context.createBrowser(queue);
            Enumeration msgs = browser.getEnumeration();

            if (!msgs.hasMoreElements()) {
                System.out.println("No messages in queue");
            } else {
                while (msgs.hasMoreElements()) {
                    Message tempMsg = (Message) msgs.nextElement();
                    System.out.println("\nMessage: " + tempMsg);
                }
            }
        } catch (JMSException e) {
            System.err.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}
