package org.feuyeux.mq.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.mq.AirJMS2Env;
import org.hornetq.jms.server.embedded.EmbeddedJMS;

import javax.annotation.Resource;
import javax.jms.*;

public class SyncConsumer {
    Logger log= LogManager.getLogger();

    @Resource(lookup = AirJMS2Env.AIR_JMS_CF)
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = AirJMS2Env.AIR_QUEUE)
    private static Queue queue;
    @Resource(lookup = AirJMS2Env.AIR_TOPIC)
    private static Topic topic;

    public SyncConsumer(EmbeddedJMS jmsServer) throws Exception {
        connectionFactory = (ConnectionFactory) jmsServer.lookup(AirJMS2Env.AIR_JMS_CF);
        queue = (Queue) jmsServer.lookup(AirJMS2Env.AIR_QUEUE);
        topic = (Topic) jmsServer.lookup(AirJMS2Env.AIR_TOPIC);
    }

    public boolean go(String... args) {
        log.info("==== SyncConsumer go ====");
        String destType = args[0];
        Destination dest = null;
        JMSConsumer consumer;

        if (args.length != 1) {
            log.info("Program takes one argument: <dest_type>");
            return false;
        }

        log.info("Destination type is " + destType);

        if (!(destType.equals(AirJMS2Env.QUEUE) || destType.equals(AirJMS2Env.TOPIC))) {
            log.info("Argument must be \"queue\" or \"topic\"");
            return false;
        }

        try {
            if (destType.equals(AirJMS2Env.QUEUE)) {
                dest = (Destination) queue;
            } else {
                dest = (Destination) topic;
            }
        } catch (JMSRuntimeException e) {
            log.info("Error setting destination: " + e.toString());
            return false;
        }

        Message m = null;
        try (JMSContext context = connectionFactory.createContext();) {
            consumer = context.createConsumer(dest);
            int count = 0;

            while (true) {
                m = consumer.receive(1000);
                if (m != null) {
                    if (m instanceof TextMessage) {
                        log.info("Reading message: " + m.getBody(String.class));
                        count += 1;
                    } else {
                        break;
                    }
                }
            }
            log.info("Messages received: " + count);
        } catch (MessageFormatException e) {
            log.error("Message : " + m);
            log.error("Exception= " + e.toString());
        } catch (JMSException e) {
            log.error("Message : " + m);
            log.error("Exception= " + e.toString());
        }
        return true;
    }
}
