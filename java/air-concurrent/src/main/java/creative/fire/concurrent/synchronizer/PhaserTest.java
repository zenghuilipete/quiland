package creative.fire.concurrent.synchronizer;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PhaserTest {
    class PhaserTask implements Callable<Long> {
        private Phaser phaser;
        private final Integer sequence;

        public PhaserTask(Integer sequence, Phaser phaser) {
            this.sequence = sequence;
            this.phaser = phaser;
        }

        @Override
        public Long call() {
            try {
                System.out.println(sequence + " 等待进入...");
                phaser.arriveAndAwaitAdvance();
                doWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return System.nanoTime();
        }

        void doWork() throws InterruptedException {
            System.out.println("工人" + sequence + " 进来了, 开始工作");
            Thread.sleep(500);
            System.out.println("工人" + sequence + " 完成工作");
        }
    }

    public double timeTasks(int times) throws InterruptedException {
        final Phaser phaser = new Phaser(times);
        phaser.register();phaser.register();
        long start = System.currentTimeMillis();
        ExecutorService exec = Executors.newFixedThreadPool(times);
        ArrayList<Future<Long>> taskList = new ArrayList<Future<Long>>();
        for (int i = 0; i < times; i++) {
            Future<Long> f = exec.submit(new PhaserTask(i, phaser));
            taskList.add(f);
        }
        phaser.arriveAndDeregister(); phaser.arriveAndDeregister();
        exec.shutdown();

        for (int i = 0; i < times; i++) {
            try {
                Long sequence = taskList.get(i).get();
                System.err.println("工人" + i + " 离开: " + sequence);
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
        double elapsed = new PhaserTest().timeTasks(times);
        System.out.println("耗时：" + elapsed + " 秒");
    }
}
