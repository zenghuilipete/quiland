package creative.fire.multithread;

public class Test2 {
	public static void main(String[] args) {
		new Test2().init();

	}

	public void init() {
		final Business business = new Business();
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 50; i++) {
					business.SubThread(i);
				}
			}

		}

		).start();

		for (int i = 0; i < 50; i++) {
			business.MainThread(i);
		}
	}

	private class Business {
		boolean bShouldSub = true;//这里相当于定义了控制该谁执行的一个信号灯

		public synchronized void MainThread(int i) {
			if (bShouldSub)
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			for (int j = 0; j < 5; j++) {
				System.out.println(Thread.currentThread().getName() + ":i=" + i + ",j=" + j);
			}
			bShouldSub = true;
			this.notify();

		}

		public synchronized void SubThread(int i) {
			if (!bShouldSub)
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			for (int j = 0; j < 10; j++) {
				System.out.println(Thread.currentThread().getName() + ":i=" + i + ",j=" + j);
			}
			bShouldSub = false;
			this.notify();
		}
	}
}
