package org.feuyeux.mq.basic;

import org.feuyeux.mq.AirJMS2Env;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.IOException;
import java.io.InputStreamReader;

public class AsyncConsumer {
    @Resource(lookup = AirJMS2Env.AIR_JMS_CF)
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = AirJMS2Env.AIR_QUEUE)
    private static Queue queue;
    @Resource(lookup = AirJMS2Env.AIR_TOPIC)
    private static Topic topic;

    public static void go(String... args) {
        String destType;
        Destination dest = null;
        JMSConsumer consumer;
        TextListener listener;
        InputStreamReader inputStreamReader;
        char answer = '\0';

        if (args.length != 1) {
            System.err.println("Program takes one argument: <dest_type>");
            System.exit(1);
        }

        destType = args[0];
        System.out.println("Destination type is " + destType);

        if (!(destType.equals("queue") || destType.equals("topic"))) {
            System.err.println("Argument must be \"queue\" or \"topic\"");
            System.exit(1);
        }

        try {
            if (destType.equals("queue")) {
                dest = (Destination) queue;
            } else {
                dest = (Destination) topic;
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Error setting destination: " + e.toString());
            System.exit(1);
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
            System.out.println("To end program, type Q or q, then <return>");
            inputStreamReader = new InputStreamReader(System.in);

            while (!((answer == 'q') || (answer == 'Q'))) {
                try {
                    answer = (char) inputStreamReader.read();
                } catch (IOException e) {
                    System.err.println("I/O exception: " + e.toString());
                }
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}
