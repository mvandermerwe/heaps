package cs2420;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Timing experiments for the Heap class
 * 
 * @author Mark Van der Merwe and Roman Clark
 *
 */
public class Timing {

	// Control N in tests.
	public static final int START = 1000;
	public static final int END = 500000;
	public static final int INCREMENT = 50000;

	// Control repetition of tests for accuracy.
	public static final int TESTS = 100;

	private enum DataOrder {
		RANDOM, IN_ORDER, BACK_ORDER;
	}

	/**
	 * Run timing tests on insertion of N values into an empty heap and 1 value
	 * into a heap with N values. Also track swaps for the each of those
	 * situations.
	 */
	public static void testInserting(DataOrder dataType) {
		StringBuilder insertTimes = new StringBuilder();
		Random generator = new Random();

		// Increment through N values.
		for (int n = START; n <= END; n += INCREMENT) {

			long totalInsertionTime = 0;
			double totalInsertionSwaps = 0;
			long insertOneTime = 0;
			double insertOneSwaps = 0;

			for (int test = 0; test < TESTS; test++) {
				Heap<Integer> heap = new Heap<Integer>();
				// First time inserting N elements to empty heap.
				for (int index = 0; index < n; index++) {
					Integer temp = 0;
					
					switch(dataType) {
					case RANDOM:
						temp = generator.nextInt(n);
						break;
					case IN_ORDER:
						temp = index;
						break;
					case BACK_ORDER:
						temp = n - index;
						break;
					}
					 
					long startTime = System.nanoTime();
					heap.add(temp);
					totalInsertionTime += (System.nanoTime() - startTime) / TESTS;
				}
				totalInsertionSwaps += (double) heap.get_swaps() / (double) TESTS;
				heap.clear_swaps();

				// Time inserting 1 element into N element heap.
				Integer temp = generator.nextInt(n);
				long startTime = System.nanoTime();
				heap.add(temp);
				insertOneTime += (System.nanoTime() - startTime) / TESTS;
				insertOneSwaps += (double) heap.get_swaps() / (double) TESTS;
				heap.clear_swaps();

				// Reset the heap for the next go around.
				heap.clear();
			}

			// Write for N vals to file.
			insertTimes.append(n + "," + totalInsertionTime + "," + totalInsertionSwaps + "," + insertOneTime + ","
					+ insertOneSwaps + "\n");
		}

		// Write vals to file.
		sendToFile(insertTimes, "insertTimes" + dataType +".csv");
	}

	public static void testDeleting() {
		StringBuilder deleteTimes = new StringBuilder();
		Random generator = new Random();

		// Increment through N values.
		for (int n = START; n <= END; n += INCREMENT) {

			long totalDeletionTime = 0;
			double totalDeletionSwaps = 0;
			long deleteOneTime = 0;
			double deleteOneSwaps = 0;

			for (int test = 0; test < TESTS; test++) {
				Heap<Integer> heap = new Heap<Integer>();
				// Create a heap of N elements
				for (int index = 0; index < n + 1; index++) {
					Integer temp = generator.nextInt(n + 1);
					heap.add(temp);
				}
				heap.clear_swaps();
				long startTime = System.nanoTime();
				heap.dequeue();
				deleteOneTime += (System.nanoTime() - startTime) / TESTS;

				deleteOneSwaps += (double) heap.get_swaps() / (double) TESTS;
				heap.clear_swaps();

				for (int index = 0; index < n; index++) {
					startTime = System.nanoTime();
					heap.dequeue();
					totalDeletionTime += (System.nanoTime() - startTime) / TESTS;
				}
				totalDeletionSwaps += (double) heap.get_swaps() / (double) TESTS;
				heap.clear_swaps();

				// Reset the heap for the next go around.
				heap.clear();
			}

			// Write for N vals to file.
			deleteTimes.append(n + "," + totalDeletionTime + "," + totalDeletionSwaps + "," + deleteOneTime + ","
					+ deleteOneSwaps + "\n");
		}

		// Write vals to file.
		sendToFile(deleteTimes, "deleteTimes.csv");
	}

	public static void main(String args[]) {
		for (int warmup = 0; warmup < 1000; warmup++) {
			System.nanoTime();
		}

		// testInserting();
		 testDeleting();
	}

	/**
	 * Helper method for writing our data to a CSV file.
	 * 
	 * @param fileData
	 *            - the data to be put into the file.
	 * @param filename
	 *            - the name of the file to write to.
	 */
	private static void sendToFile(StringBuilder fileData, String filename) {
		try {
			FileWriter csvWriter = new FileWriter(filename);
			csvWriter.write(fileData.toString());
			csvWriter.close();
		} catch (IOException e) {
			System.out.println("Unable to write to file. Here is the test data, though:");
			System.out.print(fileData.toString());
		}
	}
}
