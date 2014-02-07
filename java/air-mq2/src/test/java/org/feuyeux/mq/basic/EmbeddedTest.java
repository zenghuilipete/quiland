package org.feuyeux.mq.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.mq.AirJMS2Env;
import org.hornetq.jms.server.embedded.EmbeddedJMS;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;



public class EmbeddedTest {
    Logger log= LogManager.getLogger();

    EmbeddedJMS jmsServer = null;

    @Before
    public void tearUp() throws Exception {
        jmsServer = buildServer();
        log.info("Started Embedded JMS Server");
    }

    @Test
    public void testQueue() {
        try {
            Producer p = new Producer(jmsServer);
            SyncConsumer c = new SyncConsumer(jmsServer);
            AsyncConsumer ac = new AsyncConsumer(jmsServer);
            MessageBrowser browser = new MessageBrowser(jmsServer);

            p.go(AirJMS2Env.QUEUE, 3);
            browser.go();
            c.go(AirJMS2Env.QUEUE);

            p.go(AirJMS2Env.QUEUE, 3);
            ac.go(AirJMS2Env.QUEUE);
            browser.go();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testTopic() {
        try {
            final Producer p = new Producer(jmsServer);
            final SyncConsumer c = new SyncConsumer(jmsServer);
            final AsyncConsumer ac = new AsyncConsumer(jmsServer);
            MessageBrowser browser = new MessageBrowser(jmsServer);
            FutureTask<Boolean> f = new FutureTask<Boolean>(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return c.go(AirJMS2Env.TOPIC);
                }
            });

            Thread t = new Thread(f);
            t.setName("SyncConsumer Thread");
            t.start();

            FutureTask<Boolean> af = new FutureTask<Boolean>(new Runnable() {
                @Override
                public void run() {
                    ac.go(AirJMS2Env.TOPIC);
                }
            }, Boolean.TRUE);

            Thread at = new Thread(af);
            at.setName("AsyncConsumer Thread");
            at.start();

            p.go(AirJMS2Env.TOPIC, 3);
            if (!f.get()) {
                Assert.fail();
            }
            if (!af.get()) {
                Assert.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @After
    public void tearDown() throws Exception {
        if (jmsServer != null) {
            jmsServer.stop();
            log.info("Stopped Embedded JMS Server");
        }
    }

    private EmbeddedJMS buildServer() throws Exception {
        EmbeddedJMS jmsServer;
        jmsServer = new EmbeddedJMS();
        jmsServer.start();
        return jmsServer;
    }
}
