package cs2420;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items. The queue is
 * implemented as a min heap.
 * 
 * The min heap is implemented implicitly as an array.
 * 
 * @author Roman Clark and Mark Van der Merwe
 */
public class Heap<Type> implements Priority_Queue<Type> {

	/**
	 * The number of elements in the heap (NOT: the capacity of the array)
	 */
	private int size;

	/**
	 * The implementation array used to store heap values.
	 * 
	 * NOTE: the capacity of the array will be larger (or equal) to the size (of
	 * the heap).
	 * 
	 * WARNING: to simplify math, you are to use a 1 INDEXED array. (this means
	 * you ignore 0 bucket) and the capacity of the array has to be 1 larger
	 */
	private Type[] heap_array;

	/**
	 * If the user provides a comparator, use it instead of default comparable
	 */
	private Comparator<? super Type> comparator;
	
	/**
	 * Keep track of the number of element swaps done through insertions, deletions, building heaps, etc.
	 */
	private int swaps;

	/**
	 * Constructs an empty priority queue. Orders elements according to their
	 * natural ordering (i.e., AnyType is expected to be Comparable)
	 * 
	 * AnyType is not forced to be Comparable.
	 */
	@SuppressWarnings("unchecked")
	public Heap() {
		size = 0;
		comparator = null;
		heap_array = (Type[]) new Object[10];
	}

	/**
	 * Construct an empty priority queue with a specified comparator.
	 * 
	 * Orders elements according to the input Comparator (i.e., AnyType need not
	 * be Comparable).
	 */
	@SuppressWarnings("unchecked")
	public Heap(Comparator<? super Type> c) {
		super();
		comparator = c;
		heap_array = (Type[]) new Object[10];
	}

	/**
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException
	 *             if this priority queue is empty. (Runs in logarithmic time.)
	 */
	public Type dequeue() throws NoSuchElementException {
		// if the heap is empty, throw a NoSuchElementException
		if (size == 0) {
			throw new NoSuchElementException();
		}

		// store the minimum item so that it may be returned at the end
		Type minItem = heap_array[1];

		// replace the item at minIndex with the last item in the tree
		swap(1, size);
//		heap_array[size] = null;

		// update size
		size--;

		// percolate the item at minIndex down the tree until heap order is
		// restored
		percolateDown(1);

		// return the minimum item that was stored
		return minItem;
	}

	/**
	 * Adds an item to this priority queue. (Runs in logarithmic time.) Can
	 * sometimes terminate early.
	 * 
	 * WARNING: make sure you use the compare method defined for you below
	 * 
	 * @param x
	 *            -- the item to be inserted
	 */
	public void add(Type x) {
		// if the array is full, double its capacity
		if (heap_array.length - 1 == size) {
			resize();
		}

		// add the new item to the next available node in the tree, so that
		// complete tree structure is maintained
		heap_array[size + 1] = x;

		// update size
		size++;

		// percolate the new item up the levels of the tree until heap order is
		// restored
		percolateUp(size);
	}

	/**
	 * Move provided index up the array until it is at correct position relative
	 * to those around it.
	 * 
	 * @param index
	 *            - index to percolate up.
	 */
	private void percolateUp(int index) {
		Type element = heap_array[index];

		// while its parent is greater than it, swap them.
		while (index > 1 && compare(element, heap_array[index / 2]) < 0) {
			swap(index, index / 2);
			index = index / 2;
		}

	}

	/**
	 * If element at index is greater than its children, percolate down until in
	 * correct position relative to those around it.
	 * 
	 * @param index
	 *            - Initial location of element to percolate down
	 */
	private void percolateDown(int index) {

		// If no children, can't percolate down further
		if (index * 2 > size) {
			return;
		}

		Type element = heap_array[index];

		// Find minimum element between two children
		int compIndex = minElement(index * 2, index * 2 + 1);

		// Continually swap parent with minimum child if children larger than
		// parent
		// If minElement returns -1, no children so break loop.
		while (index < size && (compIndex == -1 ? false : compare(element, heap_array[compIndex]) > 0)) {
			swap(index, compIndex);
			index = compIndex;
			compIndex = minElement(index * 2, index * 2 + 1);
		}
	}

	/**
	 * Finds the minimum element at two indices in the heap array and returns
	 * its location
	 * 
	 * @param index1
	 *            - Location of first element
	 * @param index2
	 *            - Location of second element - should be next element after
	 *            index1.
	 * @return - Location of minimum, -1 if no children.
	 */
	public int minElement(int index1, int index2) {
		// If illegal index throw new exception.
		if (index1 < 1 || index2 < 1) {
			throw new NoSuchElementException();
		}

		if (index1 > size) {
			// If index1 above size, no children.
			return -1;
		} else if (index2 > size) {
			// If index2 above size, one child hence one min.
			return index1;
		} else {
			// If both are
			Type element1 = heap_array[index1];
			Type element2 = heap_array[index2];
			if (compare(element1, element2) > 0) {
				return index2;
			}
			return index1;
		}
	}

	/**
	 * Swaps two provided items.
	 * 
	 * @param index1
	 *            - index of first item.
	 * @param index2
	 *            - index of second item.
	 */
	public void swap(int index1, int index2) {
		Type element1 = heap_array[index1];
		heap_array[index1] = heap_array[index2];
		heap_array[index2] = element1;
		swaps++;
	}

	/**
	 * Resizes backing array by doubling capacity.
	 */
	@SuppressWarnings("unchecked")
	public void resize() {
		// New capacity is twice the size of old.
		Type[] newArray = (Type[]) new Object[2 * heap_array.length];

		// Copy over old elements.
		for (int index = 1; index < heap_array.length; index++) {
			newArray[index] = heap_array[index];
		}

		heap_array = newArray;
	}

	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try (PrintWriter out = new PrintWriter(filename)) {
			out.println(this);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by
	 * the user at construction time, or Comparable, if no Comparator was
	 * provided.
	 */
	@SuppressWarnings("unchecked")
	private int compare(Type lhs, Type rhs) {
		if (comparator == null) {
			return ((Comparable<? super Type>) lhs).compareTo(rhs); // safe to
																	// ignore
																	// warning
		}

		// We won't test your code on non-Comparable types if we didn't supply a
		// Comparator
		return comparator.compare(lhs, rhs);
	}

	/**
	 * @return a copy of the array used in the heap
	 */
	public Object[] toArray() {
		Object[] copy_of_array = new Object[size + 1];

		for (int i = 1; i <= size; i++) {
			copy_of_array[i] = heap_array[i];
		}

		return copy_of_array;
	}

	/**
	 * @return a string representing the DOT data of the heap
	 * 
	 *         This can be further augmented to print out any instrumented
	 *         values that you think are important. Note: To allow them not to
	 *         conflict with the DOT notation, simply preface them with the //
	 *         comment characters: e.g., "// numbers of insertions: 1234"
	 */
	@Override
	public String toString() {
		String result = "digraph Heap {\n\tnode [shape=record]\n";
		for (int i = 1; i <= size; i++) {
			result += "\tnode" + i + " [label = \"<f0> |<f1> " + heap_array[i] + "|<f2> \"]\n";
			if (((i * 2)) <= size)
				result += "\tnode" + i + ":f0 -> node" + ((i * 2)) + ":f1\n";
			if (((i * 2) + 1) <= size)
				result += "\tnode" + i + ":f2 -> node" + ((i * 2) + 1) + ":f1\n";
		}
		result += "}";

		result += "\n//--------------------------------------------\n"
				+ "// Any other info you want to put about your heap";

		return result;
	}

	////////////////////////////////////////////////////////////////////////////
	/**
	 * 1) copy data from array into heap storage 2) do an "in place" creation of
	 * the heap
	 * 
	 * @param array
	 *            - random data (unordered)
	 */
	@SuppressWarnings("unchecked")
	public void build_heap_from_array(Type[] array) {
		// WARNING: advanced work only worth 2.5% of grade
		// If you do not fully implement this code, leave it blank
		this.heap_array = (Type[]) new Object[array.length+1];
		for(int index = 1; index < heap_array.length; index++){
			heap_array[index] = array[index-1];
		}
		size = array.length;
		int halfway = size/2;
		for(int index = halfway; index > 0; index--){
			percolateDown(index);
		}
		
	}

	/**
	 * convert the heap array into a sorted array from largest to smallest
	 * 
	 * Note: this destroys the heap property of the array and should be a
	 * terminal operation, which is not what we would likely do in a real
	 * program, but is appropriate to for our purposes (i.e., understanding how
	 * heap sort works in place).
	 * 
	 */
	public void heap_sort() {
		int tempSize = size;
		
		while(size > 0) {
			dequeue();
		}
		
		this.size = tempSize;
	}
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Reset swap count for timing analysis
	 */
	public void clear_swaps() {
		swaps = 0;
	}
	
	public int get_swaps() {
		return swaps;
	}
	
	
	
	/**
	 * Return the value of the smallest item in our heap.
	 * 
	 * @return - smallest value, null if empty.
	 */
	@Override
	public Type peek() {
		if (size == 0) {
			return null;
		}
		return heap_array[1];
	}

	/**
	 * Returns the number of elements in the heap.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Clear the heap.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void clear() {

		heap_array = (Type[]) new Object[10];
		size = 0;

	}

}