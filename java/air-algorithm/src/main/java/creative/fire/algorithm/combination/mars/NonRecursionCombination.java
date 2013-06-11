package creative.fire.algorithm.combination.mars;

import java.util.ArrayList;

public class NonRecursionCombination implements Combination {
	private int targetLength;
	private int sourceLength;
	private int targetArray[];
	private int sourceArray[];
	private ArrayList<int[]> target = new ArrayList<int[]>();

	public ArrayList<int[]> get(int[] source) {
		sourceArray = source;
		sourceLength = source.length;
		for (targetLength = sourceLength; targetLength > 0; targetLength--) {
			targetArray = new int[targetLength];
			nonRecursion();
		}
		return target;
	}

	public void nonRecursion() {
		int trace = 0;
		for (int i = 0; i < sourceLength;) {
			if (sourceLength - i == targetLength - trace) {
				for (int j = 0; j < targetLength - trace; j++)
					targetArray[trace + j] = sourceArray[i + j];
				target.add(targetArray.clone());
				if (trace == 0)
					break;
				i = targetArray[--trace];
			} else if (trace == targetLength) {
				target.add(targetArray.clone());
				i = targetArray[--trace];
			} else
				targetArray[trace++] = sourceArray[i++];
		}
	}
}