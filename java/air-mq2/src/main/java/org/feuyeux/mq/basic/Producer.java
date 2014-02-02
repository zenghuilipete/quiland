package org.feuyeux.mq.basic;

import org.feuyeux.mq.AirJMS2Env;

import javax.annotation.Resource;
import javax.jms.*;

public class Producer {
    @Resource(lookup = AirJMS2Env.AIR_JMS_CF)
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = AirJMS2Env.AIR_QUEUE)
    private static Queue queue;
    @Resource(lookup = AirJMS2Env.AIR_TOPIC)
    private static Topic topic;

    public static void go(String style, final int number) {
        String message;
        System.out.println("Destination type is " + style);
        if (!(style.equals("queue") || style.equals("topic"))) {
            System.err.println("Argument must be \"queue\" or " + "\"topic\"");
            System.exit(1);
        }
        Destination dest = null;
        try {
            if (style.equals("queue")) {
                dest = (Destination) queue;
            } else {
                dest = (Destination) topic;
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Error setting destination: " + e.toString());
            System.exit(1);
        }

        /*
         * Within a try-with-resources block, create context.
         * Create producer and message.
         * Send messages, varying text slightly.
         * Send end-of-messages message.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            int count = 0;
            for (int i = 0; i < number; i++) {
                message = "This is message " + (i + 1) + " from producer";
                // Comment out the following line to send many messages
                System.out.println("Sending message: " + message);
                JMSProducer producer = context.createProducer();
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT).send(dest, message);
                count += 1;
            }
            System.out.println("Messages sent: " + count);
            
            /*
             * Send a non-text control message indicating end of
             * messages.
             */
            context.createProducer().send(dest, context.createMessage());
            // Uncomment the following line if you are sending many messages
            // to two synchronous consumers
            // context.createProducer().send(dest, context.createMessage());
        } catch (JMSRuntimeException e) {
            System.err.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}
