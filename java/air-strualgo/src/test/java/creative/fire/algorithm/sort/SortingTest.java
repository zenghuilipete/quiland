package creative.fire.algorithm.sort;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Sort Algorithm Test Unit
 * 
 * @author feuyeux@gmail.com
 * 2010-12-31
 */
public class SortingTest {
	private ISort sorter;
	private int[] finalArray;
	private int[] initialArray;
	private static final int[] desiredArray;
	private static boolean descend = true;

	static {
		if (descend)
			desiredArray = new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		else
			desiredArray = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		System.out.println("Happy New Year 2011!\nI glad to share it with you all\nfeuyeux@gmail.com\n\n");
	}

	@Before
	public void before(){
		initialArray = new int[] { 8, 6, 1, 4, 9, 2, 5, 3, 0, 7 };
	}
	
	@After
	public void after(){
		
	}
	
	@Test
	public void testBubbleSort() {
		sorter = new BubbleSort(descend);
		finalArray = sorter.sort(initialArray);
		p("testBubbleSort", finalArray);
		assertArrayEquals(finalArray, desiredArray);
	}

	@Test
	public void testSelectionSort() {
		sorter = new SelectionSort(descend);
		finalArray = sorter.sort(initialArray);
		p("testSelectionSort", finalArray);
		assertArrayEquals(finalArray, desiredArray);
	}

	@Test
	public void testInsertionSort() {
		sorter = new InsertionSort(descend);
		finalArray = sorter.sort(initialArray);
		p("testInsertionSort", finalArray);
		assertArrayEquals(finalArray, desiredArray);
	}

	@Test
	public void testShellSort() {
		sorter = new ShellSort(descend);
		finalArray = sorter.sort(initialArray);
		p("testShellSort", finalArray);
		assertArrayEquals(finalArray, desiredArray);
	}

	@Test
	public void testMergeSort() {
		sorter = new MergeSort(descend);
		finalArray = sorter.sort(initialArray);
		p("testMergeSort", finalArray);
		assertArrayEquals(finalArray, desiredArray);
	}

	@Test
	public void testQuickSort() {
		sorter = new QuickSort(descend);
		finalArray = sorter.sort(initialArray);
		p("testQuickSort", finalArray);
		assertArrayEquals(finalArray, desiredArray);
	}

	private void p(String name, int[] b) {
		if(!Sort.muted)
			return;
		System.out.println(name + ":");
		for (int i = 0; i < b.length; i++) {
			if (b.length - 1 == i)
				System.out.println(b[i]);
			else
				System.out.print(b[i] + "\t");
		}
	}
}
