package creative.fire.notireq;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedule {
    ScheduledExecutorService excutor;
    private long startTime;

    public Schedule() {
        excutor = Executors.newSingleThreadScheduledExecutor();
        startTime = System.currentTimeMillis();
    }

    public long getTotalTime() {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public String getSeconds() {
        long s = getTotalTime() / 1000;
        return s + " seconds";
    }

    public void schedule(Runnable command, long delay, TimeUnit unit) {
        excutor.schedule(command, delay, unit);
    }

    public void shutdown() {
        excutor.shutdownNow();
    }
}
