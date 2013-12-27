package creative.fire.algorithm.combination;

public class CombinationAlgorithm {
	int sourceArray[];
	static int targetArray[];
	final static int sourceLength = 3;
	static int targetLength = sourceLength;

	public static void main(String arf[]) {
		CombinationAlgorithm demo = new CombinationAlgorithm();
		for (; targetLength > 0; targetLength--) {
			targetArray = new int[targetLength];
			System.out.println("********************");
			demo.recursion(0, 0);
		}
		System.out.println("\n\n");
		for (targetLength = sourceLength; targetLength > 0; targetLength--) {
			targetArray = new int[targetLength];
			System.out.println("********************");
			demo.nonRecursion();
		}
	}

	public CombinationAlgorithm() {
		sourceArray = new int[sourceLength];
		for (int i = 1; i <= sourceLength; i++)
			sourceArray[i - 1] = i;
	}

	public void recursion(int sourceIndex, int targetIndex) {
		if (targetIndex == targetLength) {
			for (int i = 0; i < targetLength; i++)
				System.out.print(targetArray[i] + " ");
			
			System.out.println();
		} else if (sourceLength - sourceIndex == targetLength - targetIndex) {
			for (int i = 0; i < targetLength - targetIndex; i++)
				targetArray[targetIndex + i] = sourceArray[sourceIndex + i];

			for (int i = 0; i < targetLength; i++)
				System.out.print(targetArray[i] + " ");
			
			System.out.println();
		} else {
			targetArray[targetIndex] = sourceArray[sourceIndex];
			recursion(sourceIndex + 1, targetIndex + 1);
			recursion(sourceIndex + 1, targetIndex);
		}
	}

	public void nonRecursion() {
		int trace = 0;
		for (int i = 0; i < sourceLength;) {
			if (sourceLength - i == targetLength - trace) {
				for (int j = 0; j < targetLength - trace; j++)
					targetArray[trace + j] = sourceArray[i + j];
				for (int j = 0; j < targetLength; j++)
					System.out.print(targetArray[j] + " ");
				System.out.println();
				if (trace == 0)
					break;
				i = targetArray[--trace];
			} else if (trace == targetLength) {
				for (int j = 0; j < targetLength; j++)
					System.out.print(targetArray[j] + " ");
				System.out.println();
				i = targetArray[--trace];
			} else
				targetArray[trace++] = sourceArray[i++];
		}
	}
}