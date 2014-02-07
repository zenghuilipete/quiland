package org.feuyeux.mq.basic;

import org.feuyeux.mq.AirJMS2Env;
import org.hornetq.jms.server.embedded.EmbeddedJMS;

import javax.annotation.Resource;
import javax.jms.*;

public class Producer {
    @Resource(lookup = AirJMS2Env.AIR_JMS_CF)
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = AirJMS2Env.AIR_QUEUE)
    private static Queue queue;
    @Resource(lookup = AirJMS2Env.AIR_TOPIC)
    private static Topic topic;

    public Producer() {

    }

    public Producer(EmbeddedJMS jmsServer) throws Exception {
        connectionFactory = (ConnectionFactory) jmsServer.lookup(AirJMS2Env.AIR_JMS_CF);
        queue = (Queue) jmsServer.lookup(AirJMS2Env.AIR_QUEUE);
        topic = (Topic) jmsServer.lookup(AirJMS2Env.AIR_TOPIC);
    }

    public void go(String style, final int number) {
        System.out.println("==== Producer go ====");
        String message;
        if (!(style.equals(AirJMS2Env.QUEUE) || style.equals(AirJMS2Env.TOPIC))) {
            System.err.println("Argument must be \"queue\" or " + "\"topic\"");
            System.exit(1);
        } else {
            System.out.println("Destination type is " + style);
        }

        Destination dest = null;
        try {
            if (style.equals(AirJMS2Env.QUEUE)) {
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
                message = "This is " + style + " message " + (i + 1) + " from producer";
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
        }
    }
}
