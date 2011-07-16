package mobi.pereira.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import mobi.pereira.forkjoin.mergesort.ParrallelMergeSortAction;
import mobi.pereira.forkjoin.mergesort.SequentialMergeSort;

public class Main {

	private final static ForkJoinPool mainPool = new ForkJoinPool(5);

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		int[] reference = generateRandomSequence(3000000);
		int[] workingCopy = Arrays.copyOf(reference, reference.length);
		long start = System.currentTimeMillis();
		ParrallelMergeSortAction mergeAction = new ParrallelMergeSortAction(
				workingCopy, 0, workingCopy.length);
		mainPool.invoke(mergeAction);
		System.err.println("> " + (System.currentTimeMillis() - start));

		workingCopy = Arrays.copyOf(reference, reference.length);

		start = System.currentTimeMillis();
		Thread monoThreaded = new Thread(new SequentialMergeSort(workingCopy,
				0, workingCopy.length));
		monoThreaded.start();
		monoThreaded.join();
		System.err.println("> " + (System.currentTimeMillis() - start));
	}

	private static int[] generateRandomSequence(int length) {
		int[] result = new int[length];
		for (int i = 0; i < length; ++i) {
			result[i] = (int) (Math.random() * 2 * length);
		}
		return result;
	}

}
