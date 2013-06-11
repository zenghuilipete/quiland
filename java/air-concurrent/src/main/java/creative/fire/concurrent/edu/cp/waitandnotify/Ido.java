package creative.fire.concurrent.edu.cp.waitandnotify;

import java.util.ArrayList;

/**
 * @author feuyeux@gmail.com 2011-3-12
 */

public class Ido {
	public void edu() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		new Producer(list).start();
		new Consumer(list).start();
	}

	public static void main(String[] args) throws Exception {
		Ido ido = new Ido();
		ido.edu();
	}
}
