package org.feuyeux.mq.basic;

import org.feuyeux.mq.AirJMS2Env;

import javax.annotation.Resource;
import javax.jms.*;

public class SyncConsumer {
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
         * Receive all text messages from destination until
         * a non-text message is received indicating end of
         * message stream.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            consumer = context.createConsumer(dest);
            int count = 0;
            
            while (true) {
                Message m = consumer.receive(1000);

                if (m != null) {
                    if (m instanceof TextMessage) {
                        // Comment out the following two lines to receive
                        // a large volume of messages
                        System.out.println(
                                "Reading message: " + m.getBody(String.class));
                        count += 1;
                    } else {
                        break;
                    }
                }
            }
            System.out.println("Messages received: " + count);
        } catch (JMSException e) {
            System.err.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}
