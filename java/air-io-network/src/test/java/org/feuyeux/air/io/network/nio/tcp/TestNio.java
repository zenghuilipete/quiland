package org.feuyeux.air.io.network.nio.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Created by feuyeux@gmail.com
 * Date: Jan 27 2014
 * Time: 12:07 PM
 */
public class TestNio {
    private static final Logger logger = LogManager.getLogger(TestNio.class);
    static final int OP_READ = 1;//1 << 0;
    static final int OP_WRITE = 1 << 2;
    static final int OP_CONNECT = 1 << 3;
    static final int OP_ACCEPT = 1 << 4;

    @Test
    public void testBitOp() {
        /*16 8 4 1 29 28*/
        logger.debug("OP_ACCEPT={} OP_CONNECT={} OP_WRITE={} OP_READ={}", OP_ACCEPT, OP_CONNECT, OP_WRITE, OP_READ);
        int interestingKey = OP_ACCEPT | OP_CONNECT | OP_WRITE | OP_READ;
        Assert.assertEquals((byte) 0B0001_1101, interestingKey);
        logger.debug(interestingKey);
        interestingKey &= ~OP_READ;
        Assert.assertEquals((byte) 0B0001_1100, interestingKey);
        logger.debug(interestingKey);
    }
}
