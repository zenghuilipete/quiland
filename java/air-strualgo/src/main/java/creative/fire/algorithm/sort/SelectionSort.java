package creative.fire.algorithm.sort;

/**
 * Bubble Sort Implementation
 * @author feuyeux@gmail.com
 * 2010-12-31
 */
public class SelectionSort extends Sort {

	public SelectionSort(boolean descending) {
		super(descending);
	}

	@Override
	public int[] sort(int[] number) {
		roundEcho("Selection Sort:\n\t", number);
		int maximum = 0, tempIndex = 0;
		for (int i = number.length - 1; i > 0; i--) {
			maximum = number[i];
			tempIndex = i;
			for (int j = 0; j <= i; j++) {
				stepEcho("->\t", number, number[j], maximum);
				maximum = (compare(number[j], maximum)) ? maximum : number[tempIndex = j];
			}
			number[tempIndex] = number[i];
			number[i] = maximum;
			roundEcho("{" + (number.length - i + 1) + "}\t", number);
		}
		return number;
	}
}