package mobi.pereira.forkjoin.mergesort;

import java.util.concurrent.RecursiveAction;


public class ParrallelMergeSortAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private final int[] values;
	private final int first;
	private final int length;

	public ParrallelMergeSortAction(int[] values, int first, int length) {
		this.values = values;
		this.first = first;
		this.length = length;
	}

	@Override
	protected void compute() {
		if (length < 2) {
			// NOp
		} else {
			int midLength = length / 2;

			ParrallelMergeSortAction m1 = null;
			if (midLength >= 2) {
				m1 = new ParrallelMergeSortAction(values, first, midLength);
				m1.fork();
			}
			if (length - midLength >= 2) {
				ParrallelMergeSortAction m2 = new ParrallelMergeSortAction(values, first + midLength,
						length - midLength);
				m2.compute();
			}
			if (null != m1) {
				m1.join();
			}
			Utils.merge(values, first, first + midLength, length);
		}
	}

}
