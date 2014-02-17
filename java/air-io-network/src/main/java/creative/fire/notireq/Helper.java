package creative.fire.notireq;

import java.util.concurrent.ConcurrentHashMap;

import static java.util.concurrent.TimeUnit.*;

public class Helper {
    private static final Helper instance = new Helper();
    private final ConcurrentHashMap<String, Schedule> cache = new ConcurrentHashMap<String, Schedule>();
    private static final int timeout = 10;

    public static Helper getInstance() {
        return instance;
    }

    private Schedule addTask(final String id) {
        final Schedule schedule = new Schedule();
        schedule.schedule(new Runnable() {
            @Override
            public void run() {
                doNext(id);
                schedule.shutdown();
            }
        }, timeout, SECONDS);
        return schedule;
    }

    private void doNext(String id) {
        Schedule schedule = cache.remove(id);
        System.out.println("time out and do next well.");
        System.out.println("total time=" + schedule.getSeconds());
    }

    public void add(final String id) {
        Schedule schedule = addTask(id);
        cache.put(id, schedule);
        System.out.println("Add to cache successfully");
    }

    public void remove(final String id) {
        Schedule schedule = cache.remove(id);
        if (schedule == null) {
            System.out.println("no schedule exist.");
        } else {
            schedule.shutdown();
            System.out.println("Remove to cache successfully");
        }
    }
}
