package mobi.pereira.forkjoin.mergesort;


public class SequentialMergeSort implements Runnable {

	private final int[] values;
	private final int first;
	private final int length;

	public SequentialMergeSort(int[] values, int first, int length) {
		this.values = values;
		this.first = first;
		this.length = length;
	}

	private void compute(int first, int length) {
		if (length < 2) {
			// NOp
		} else {
			int midLength = length / 2;
			if (midLength >= 2) {
				compute(first, midLength);
			}
			if (length - midLength >= 2) {
				compute(first + midLength, length - midLength);
			}
			Utils.merge(values, first, first + midLength, length);
		}
	}

	@Override
	public void run() {
		compute(first, length);
	}
}
