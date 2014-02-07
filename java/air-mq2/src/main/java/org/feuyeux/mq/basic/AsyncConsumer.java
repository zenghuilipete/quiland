package org.feuyeux.mq.basic;

import org.feuyeux.mq.AirJMS2Env;
import org.hornetq.jms.server.embedded.EmbeddedJMS;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.InputStreamReader;

public class AsyncConsumer {
    @Resource(lookup = AirJMS2Env.AIR_JMS_CF)
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = AirJMS2Env.AIR_QUEUE)
    private static Queue queue;
    @Resource(lookup = AirJMS2Env.AIR_TOPIC)
    private static Topic topic;

    public AsyncConsumer(EmbeddedJMS jmsServer) throws Exception {
        connectionFactory = (ConnectionFactory) jmsServer.lookup(AirJMS2Env.AIR_JMS_CF);
        queue = (Queue) jmsServer.lookup(AirJMS2Env.AIR_QUEUE);
        topic = (Topic) jmsServer.lookup(AirJMS2Env.AIR_TOPIC);
    }

    public void go(String... args) {
        System.out.println("==== AsyncConsumer go ====");
        String destType;
        Destination dest = null;
        JMSConsumer consumer;
        TextListener listener;
        InputStreamReader inputStreamReader;
        char answer = '\0';

        if (args.length != 1) {
            System.err.println("Program takes one argument: <dest_type>");
            return;
        }

        destType = args[0];
        System.out.println("Destination type is " + destType);

        if (!(destType.equals(AirJMS2Env.QUEUE) || destType.equals(AirJMS2Env.TOPIC))) {
            System.err.println("Argument must be \"queue\" or \"topic\"");
            return;
        }

        try {
            if (destType.equals(AirJMS2Env.QUEUE)) {
                dest = (Destination) queue;
            } else {
                dest = (Destination) topic;
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Error setting destination: " + e.toString());
            return;
        }

        /*
         * In a try-with-resources block, create context.
         * Create consumer.
         * Register message listener (TextListener).
         * Receive text messages from destination.
         * When all messages have been received, type Q to quit.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            consumer = context.createConsumer(dest);
            listener = new TextListener();
            consumer.setMessageListener(listener);
            Thread.sleep(5000);
            System.out.println("AsyncConsumer leave.");
        } catch (JMSRuntimeException | InterruptedException e) {
            System.err.println("Exception occurred: " + e.toString());
        }
    }
}
