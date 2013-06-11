package creative.fire.concurrent.lock.stripping;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestStrippingLock {
	public static void main(String args[]) throws InterruptedException 
	{
		StrippingLock lock1 = new StrippingLock(20000000, 100);
		
		ExecutorService exec = Executors.newFixedThreadPool(16);

        long start=System.currentTimeMillis();	
		for (int i=0; i<16; i++)
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
