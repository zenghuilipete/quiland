package creative.fire.concurrent.others;

public class MemTest {

	public static void main(String[] args) {
		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println("processors:" + processors);
		long one_mega=1024*1024;
		System.out.println(Runtime.getRuntime().totalMemory()/one_mega +"M byte");
		System.out.println(Runtime.getRuntime().maxMemory()/one_mega +"M byte");
		
		Runtime rt = Runtime.getRuntime();
		long f1 = rt.freeMemory();
		int[] ints = new int[102400];
		long f2 = rt.freeMemory();
		System.out.println("Used Memory: " + (f1 - f2) / 1024 + "K byte" + "\nEach int takes " + (f1 - f2) / ints.length + " byte");
	}
}