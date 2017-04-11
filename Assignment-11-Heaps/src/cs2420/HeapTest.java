/**
 * 
 */
package cs2420;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing heap priority queue for soundness.
 * 
 * @author Roman Clark and Mark Van der Merwe
 */
public class HeapTest {

	private Heap<Integer> heap;
	private Heap<Integer> oneSizeHeap;
	private Heap<Integer> zeroSizeHeap;

	/**
	 * Creates and returns a simple Heap with a few integers filled.
	 * 
	 * @return - simple heap data structure.
	 */
	@Before
	public void createSimpleHeap() {
		heap = new Heap<>();

		// Implicitly tests adding to zero, one, and multiple size heap.
		heap.add(5);
		heap.add(6);
		heap.add(3);
		heap.add(7);
		heap.add(8);
		heap.add(1);

		oneSizeHeap = new Heap<>();
		oneSizeHeap.add(4);

		zeroSizeHeap = new Heap<>();
	}

	/**
	 * Test inserting value and generating dot file.
	 */
	@Test
	public void test_basic_insertion() {
		assertEquals(6, heap.size());

		Object[] temp = heap.toArray();

		assertArrayEquals(new Integer[] { null, 1, 6, 3, 7, 8, 5 }, temp);

		// if you want to look at your heap, uncomment this line to generate a
		// graph file,
		//
		// heap.generateDotFile("Documents/test_heap.dot");
		//
		// or uncomment this line, run the tests:
		//
		System.out.println(heap);
		//
		// and then paste the output of the console into:
		// http://www.webgraphviz.com/
	}

	/**
	 * Test dequeuing from our heaps.
	 */
	@Test
	public void test_dequeue() {
		// Simple multiple value test.
		assertEquals(1, (int) heap.dequeue());
		assertEquals(5, heap.size());
		Object[] temp = heap.toArray();
		assertArrayEquals(new Integer[] { null, 3, 6, 5, 7, 8 }, temp);

		assertEquals(3, (int) heap.dequeue());
		assertEquals(4, heap.size());
		temp = heap.toArray();
		assertArrayEquals(new Integer[] { null, 5, 6, 8, 7 }, temp);

		assertEquals(5, (int) heap.dequeue());
		assertEquals(3, heap.size());
		temp = heap.toArray();
		assertArrayEquals(new Integer[] { null, 6, 7, 8 }, temp);

		// One size test.
		assertEquals(4, (int) oneSizeHeap.dequeue());
		temp = oneSizeHeap.toArray();
		assertArrayEquals(new Integer[] { null }, temp);

		// Zero size test.
		try {
			zeroSizeHeap.dequeue();
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			// Test passes.
		}
	}

	/**
	 * Test many many inserts and many many dequeues to make sure resizing
	 * works.
	 */
	@Test
	public void test_lots_of_insertions_deletions_peeks() {
		Heap<Integer> heap = new Heap<>();

		final int COUNT = 1000;
		Random generator = new Random();

		// add COUNT elements to HEAP
		for (int element = 0; element < COUNT; element++) {
			heap.add(generator.nextInt(1000));
		}

		assertEquals(COUNT, heap.size());

		int smallest = heap.dequeue();

		// while the heap has elements
		// remove one, make sure it is larger than smallest, update smallest
		while (heap.size() > 0) {
			// System.out.println(heap.size());
			int nextSmallest = heap.dequeue();
			assertTrue(nextSmallest >= smallest);
			smallest = nextSmallest;
		}
	}

	/**
	 * Make sure we can peek correctly at the smallest value in the heap.
	 */
	public void test_peek() {
		// Test peeking into a multiple element heap.
		assertEquals(1, (int) heap.peek());
		heap.dequeue();
		assertEquals(3, (int) heap.peek());

		// Test peeking into a one element heap.
		assertEquals(4, (int) oneSizeHeap.peek());

		// Test peeking into a zero element heap.
		try {
			zeroSizeHeap.peek();
			fail("Should have thrown NoSuchElementException");
		} catch (NoSuchElementException e) {
			// Test passes.
		}
	}

	/**
	 * Test returning of index of the smaller of two provided indices.
	 */
	public void test_minimum() {
		//assertEquals()
	}

	public void test_swap() {

	}

	public void test_clear() {

	}

}
