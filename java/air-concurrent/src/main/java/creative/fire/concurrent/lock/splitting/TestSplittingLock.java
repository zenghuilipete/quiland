package creative.fire.concurrent.lock.splitting;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestSplittingLock {
	public static void main(String args[]) throws InterruptedException 
	{
		SplittingLock lock1 = new SplittingLock(200000);
		
		ExecutorService exec = Executors.newFixedThreadPool(10);

        long start=System.currentTimeMillis();
		for (int i=0; i<10; i++)
		{
			exec.execute(lock1);
		}
		exec.shutdown();
        //Waiting all the tasks to finish
        exec.awaitTermination(480, TimeUnit.SECONDS);
        long end=System.currentTimeMillis();
        System.out.println("****Run Time："+(end-start));
	}
}
