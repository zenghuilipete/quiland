package creative.fire.algorithm.sort;

/**
 * Insertion Sort Implementation
 * @author feuyeux@gmail.com
 * 2010-12-31
 */
public class InsertionSort extends Sort {
	public InsertionSort(boolean descending) {
		super(descending);
	}

	@Override
	public int[] sort(int[] number) {
		roundEcho("Insertion Sort:\n\t", number);
		int sentinel = 0;
		int j;
		for (int i = 1; i < number.length; i++) {
			sentinel = number[i];
			for (j = i - 1; j >= 0; j--) {
				stepEcho("->\t", number, sentinel, number[j]);
				if (compare(sentinel, number[j]))
					number[j + 1] = number[j];
				else {
					break;
				}
			}
			number[j + 1] = sentinel;
			roundEcho("{" + i + "}\t", number);
		}
		return number;
	}
}