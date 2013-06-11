package creative.fire.concurrent.lock.atomic;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class TestAtomicLock {
	public static void main(String args[]) throws InterruptedException 
	{
		AtomicLock lock1 = new AtomicLock(2000000);
		
		ExecutorService exec = Executors.newFixedThreadPool(10);
   
        long start=System.currentTimeMillis();	
		for (int i=0; i<10; i++)
		{
			exec.execute(lock1);
		}
        exec.shutdown();
        exec.awaitTermination(480, TimeUnit.SECONDS);
	    long end=System.currentTimeMillis();
        System.out.println("****Run Time："+(end-start));
	}
}
