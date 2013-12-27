package creative.fire.algorithm.sort;

public abstract class Sort implements ISort {
	private boolean descending;
	public static boolean muted = false;

	public Sort(boolean descending) {
		this.descending = descending;
	}

	@Override
	public boolean compare(int x, int y) {
		return isDescending() ? x > y : x <= y;
	}

	@Override
	public boolean isDescending() {
		return descending;
	}

	protected void roundEcho(String message, int[] loopArray) {
		if (muted)
			return;
		System.out.print(message + " ");
		if (loopArray.length == 0)
			return;
		for (int i = 0; i < loopArray.length; i++) {
			if (i == loopArray.length - 1)
				System.out.println(loopArray[i]);
			else
				System.out.print(loopArray[i] + "\t");
		}
	}

	protected void stepEcho(String message, int[] loopArray, int x, int y) {
		if (muted)
			return;
		System.out.print(message);
		if (loopArray.length == 0)
			return;
		for (int i = 0; i < loopArray.length; i++) {
			if (loopArray[i] == x || loopArray[i] == y) {
				System.out.print("[" + loopArray[i] + "]" + "\t");
			} else {
				System.out.print(loopArray[i] + "\t");
			}
		}
		System.out.println("[" + x + "," + y + "]");
	}
}
