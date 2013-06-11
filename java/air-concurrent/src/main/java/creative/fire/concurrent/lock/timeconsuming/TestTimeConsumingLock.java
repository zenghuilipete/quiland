package creative.fire.concurrent.lock.timeconsuming;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestTimeConsumingLock {
	public static void main(String args[]) throws InterruptedException 
	{
		TimeConsumingLock lock1 = new TimeConsumingLock(500000);
		
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
