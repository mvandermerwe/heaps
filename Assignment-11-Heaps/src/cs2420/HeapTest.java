/**
 * 
 */
package cs2420;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

/**
 * 
 * @author Roman Clark and Mark Van der Merwe
 */
public class HeapTest { 
	
	/**
	 * 
	 * @return
	 */
	public Heap<Integer> createSimpleHeap() {
		Heap<Integer> heap = new Heap<>();

		heap.add(5);
		heap.add(6);
		heap.add(3);
		heap.add(7);
		heap.add(8);
		heap.add(1);
		
		return heap;
	}

	/**
	 * 
	 */
	@Test
	public void test_basic_insertion() {
		Heap<Integer> heap = createSimpleHeap();

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
	 * 
	 */
	@Test
	public void test_dequeue() {
		Heap<Integer> heap = createSimpleHeap();
		
		assertEquals(1,(int) heap.dequeue());
		assertEquals(5, heap.size());
		Object[] temp = heap.toArray();
		assertArrayEquals(new Integer[] { null, 3, 6, 5, 7, 8 }, temp);
		
		assertEquals(3,(int) heap.dequeue());
		assertEquals(4, heap.size());
		temp = heap.toArray();
		assertArrayEquals(new Integer[] { null, 5, 6, 8, 7 }, temp);
		
		assertEquals(5,(int) heap.dequeue());
		assertEquals(3, heap.size());
		temp = heap.toArray();
		assertArrayEquals(new Integer[] { null, 6, 7, 8 }, temp);
	}

	/**
	 * 
	 */
	@Test
	public void test_lots_of_insertions_deletions_peeks() {
		Heap<Integer> heap = new Heap<>();

		final int COUNT = 1000;
		Random generator = new Random();

		// add COUNT elements to HEAP
		for(int element = 0; element < COUNT; element++) {
			heap.add(generator.nextInt(1000));
		}

		assertEquals(COUNT, heap.size());

		int smallest = heap.dequeue();

		// while the heap has elements
		// remove one, make sure it is larger than smallest, update smallest
		while(heap.size() > 0) {
			//System.out.println(heap.size());
			int nextSmallest = heap.dequeue();
			assertTrue(nextSmallest >= smallest);
			smallest = nextSmallest;
		}
	}

}
