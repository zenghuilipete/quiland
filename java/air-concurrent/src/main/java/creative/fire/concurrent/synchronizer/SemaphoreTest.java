package creative.fire.concurrent.synchronizer;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 信号
 * 控制一组活动，它们在同一时间访问特定某一资源或者执行某个操作。
 * 如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。
 * 每个 release() 添加一个许可，从而可能释放一个正在阻塞的获取者。
 * Semaphore 通常用于限制可以访问某些资源（物理或逻辑的）的线程数目。
 */
public class SemaphoreTest {
    class SemaphoreTask implements Callable<Long> {
        private Semaphore semaphore;
        private final Integer sequence;

        public SemaphoreTask(Integer sequence, Semaphore semaphore) {
            this.sequence = sequence;
            this.semaphore = semaphore;
        }

        @Override
        public Long call() {
            try {
                System.out.println(sequence);
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        }

        void doWork() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public double timeTasks(int times) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(times);
        long start = System.currentTimeMillis();
        ExecutorService exec = Executors.newFixedThreadPool(times);
        ArrayList<Future<Long>> taskList = new ArrayList<Future<Long>>();
        for (int i = 0; i < times; i++) {
            Future<Long> f = exec.submit(new SemaphoreTask(i, semaphore));
            taskList.add(f);
        }
        exec.shutdown();

        for (int i = 0; i < times; i++) {
            try {
                Long sequence = taskList.get(i).get();
                System.err.println(i + " Done @: " + sequence);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        return (end - start) / 1000d;
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        int times = Runtime.getRuntime().availableProcessors();
        System.out.println("available processors = " + times);
        if (times < 4) {
            times = 4;
        }
        double elapsed = new SemaphoreTest().timeTasks(times);
        System.out.println("耗时：" + elapsed + " 秒");
    }
}