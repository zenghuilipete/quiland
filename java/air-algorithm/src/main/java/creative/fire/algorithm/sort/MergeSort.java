package creative.fire.algorithm.sort;
/**
 * Merge Sort Implementation
 * @author feuyeux@gmail.com
 * 2010-12-31
 * 
 * need the third array
 * O(n * log n)
 */
public class MergeSort extends Sort {
	public MergeSort(boolean descending) {
		super(descending);
	}

	private int[] merge(int[] number, int temporary[], int low, int middle, int high) {
		int i = low, j = middle, k = low;

		while (i < middle && j < high) {
			stepEcho("->\t", number, number[i], number[j]);
			if (compare(number[i], number[j]))
				temporary[k++] = number[i++];
			else
				temporary[k++] = number[j++];
		}

		while (i < middle) {
			temporary[k++] = number[i++];
		}
		while (j < high) {
			temporary[k++] = number[j++];
		}
		for (i = low; i < high; i++) {
			number[i] = temporary[i];
		}
		return number;
	}

	@Override
	public int[] sort(int[] number) {
		roundEcho("Merge Sort:\n\t", number);
		int length = number.length;
		int[] temporary = new int[length]; // scratch space
		for (int index = 1; index < length; index *= 2) {
			int x = 2 * index;
			for (int low = 0; low < length; low += x)
				if (low + x <= length) {
					merge(number, temporary, low, low + index, low + x);
				} else if (low + index < length) {
					merge(number, temporary, low, low + index, length);
				}
			roundEcho("{" + index + "}\t", number);
		}
		return number;
	}
}
