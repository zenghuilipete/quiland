package creative.fire.algorithm.sort;

import java.util.ArrayList;

/**
 * Shell Sort Implementation
 * @author feuyeux@gmail.com
 * 2010-12-31
 * 
 * Donald Shell o(N2)
 * increment sequence
 */
public class ShellSort extends Sort {

	public ShellSort(boolean descending) {
		super(descending);
	}

	@Override
	public int[] sort(int[] number) {
		roundEcho("Shell Sort:\n\t", number);
		int s, t;
		int sentinel = 0;
		ArrayList<Integer> gaps = new ArrayList<Integer>();
		//3*h+1
		int gap = number.length / 2;
		gaps.add(gap);
		while (gap != 1) {
			gap = gap / 2;
			gaps.add(gap);
		}
		for (int i = 0; i < gaps.size(); i++) {
			s = gaps.get(i);
			for (int j = s; j < number.length; j++) {
				sentinel = number[j];
				t = j - s;
				for (; t >= 0 && (t <= number.length); t = t - s) {
					stepEcho("->\t", number, sentinel, number[t]);
					if (compare(sentinel, number[t])) {
						number[t + s] = number[t];
					} else {
						break;
					}
				}
				number[t + s] = sentinel;
			}
			roundEcho("{" + (i+1) + "}\t", number);
		}
		return number;
	}
}
