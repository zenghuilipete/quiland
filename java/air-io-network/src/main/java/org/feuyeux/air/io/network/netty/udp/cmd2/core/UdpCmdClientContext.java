package org.feuyeux.air.io.network.netty.udp.cmd2.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Created by feuyeux@gmail.com
 * Date: Feb 17 2014
 * Time: 3:04 PM
 */
public class UdpCmdClientContext {
    final BlockingQueue<String> cmdServerCache;
    private final ReentrantLock lock;
    private final Condition notEmpty;

    public UdpCmdClientContext() {
        cmdServerCache = new ArrayBlockingQueue<>(2);
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
    }

    void addServer(String serverIp) throws InterruptedException {
        if (!cmdServerCache.contains(serverIp)) {
            cmdServerCache.put(serverIp);
        }
    }

    String[] getServers(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (cmdServerCache.peek() == null) {
                if (nanos <= 0){
                    return null;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }
            return cmdServerCache.toArray(new String[cmdServerCache.size()]);
        } finally {
            lock.unlock();
        }
    }
}
