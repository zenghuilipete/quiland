package creative.fire.algorithm.permutation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author feuyeux@gmail.com 2011-1-2
 */
public class PermutationAlgorithm {
	private ArrayList<int[]> m_list;
	private Queue<String> log = new LinkedList<String>();

	public PermutationAlgorithm() {
		m_list = new ArrayList<int[]>();
	}

	public ArrayList<int[]> getM_list() {
		return m_list;
	}

	public Queue<String> getLog() {
		return log;
	}

	public static void main(String[] args) {
		PermutationAlgorithm permutation = new PermutationAlgorithm();
		int[] a = new int[] { 1, 2, 3, 4 };
		permutation.getLog().add(permutation.arrayInterator(a));
		permutation.permute(a, 4);

		// print out
		// for (int i = 0; i < permutation.getM_list().size(); i++) {
		// int[] array = permutation.getM_list().get(i);
		// System.out.println(permutation.arrayInterator(array));
		// }

		// more print out
		while (!permutation.getLog().isEmpty()) {
			System.out.println(permutation.getLog().remove());
		}
	}

	private String arrayInterator(int[] array) {
		StringBuilder s = new StringBuilder();
		for (int j = 0; j < array.length; j++) {
			if (j == array.length - 1)
				s.append(array[j]);
			else
				s.append(array[j] + ",");
		}
		return s.toString();
	}

	private void permute(int[] array, int n) {
		if (n == 1) {
			m_list.add(array.clone());
			return;
		}
		for (int i = 0; i < n; i++) {
			int m = n - 1;

			if (m != i) {
				log.add(getPrefix(4 - m) + "swap1: &" + i + ", &" + m);
				swap(array, i, m);
				log.add(getPrefix(4 - m) + arrayInterator(array));
			}

			permute(array, m);

			if (m != i) {
				log.add(getPrefix(4 - m) + "swap2: &" + i + ", &" + m);
				swap(array, i, m);
				log.add(getPrefix(4 - m) + arrayInterator(array));
			}
		}
	}

	private String getPrefix(int n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n; i++) {
			s.append("-");
		}
		return s.toString();
	}

	private void swap(int[] numbers, int x, int y) {
		int t = numbers[x];
		numbers[x] = numbers[y];
		numbers[y] = t;
	}
}
