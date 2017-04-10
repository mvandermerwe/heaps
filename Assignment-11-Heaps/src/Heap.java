package cs2420;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items.
 * The queue is implemented as a min heap.
 * 
 * The min heap is implemented implicitly as an array.
 * 
 * @author
 */
public class Heap<Type> implements Priority_Queue<Type>
{
	/** 
	 * The number of elements in the heap (NOT: the capacity of the array)
	 */
	private int							size;

	/**
	 * The implementation array used to store heap values.
	 * 
	 * NOTE: the capacity of the array will be larger (or equal) to the size (of the heap).
	 * 
	 * WARNING: to simplify math, you are to use a 1 INDEXED array. (this means you ignore 0 bucket) and
	 * the capacity of the array has to be 1 larger
	 */
	private Type[]					heap_array;

	/**
	 * If the user provides a comparator, use it instead of default comparable
	 */
	private Comparator<? super Type>	comparator;

	/**
	 * Constructs an empty priority queue. Orders elements according
	 * to their natural ordering (i.e., AnyType is expected to be Comparable)
	 * 
	 * AnyType is not forced to be Comparable.
	 */
	@SuppressWarnings("unchecked")
	public Heap()
	{
		size = 0;
		comparator = null;
		heap_array = (Type[]) new Object[10];
	}

	/**
	 * Construct an empty priority queue with a specified comparator.
	 * 
	 * Orders elements according to the input Comparator (i.e., AnyType need not be Comparable).
	 */
	@SuppressWarnings("unchecked")
	public Heap( Comparator<? super Type> c )
	{
		super();
		comparator = c;
	}

	/*
	 * 
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException if this priority queue is empty.
	 * (Runs in logarithmic time.)
	 */
	public Type dequeue() throws NoSuchElementException
	{
		// FILL IN -- do not return null
		// if the heap is empty, throw a NoSuchElementException
		// store the minimum item so that it may be returned at the end
		// replace the item at minIndex with the last item in the tree
		// update size
		// percolate the item at minIndex down the tree until heap order is restored
		// It is STRONGLY recommended that you write a percolateDown helper method!
		// return the minimum item that was stored
		return null;
	}

	/**
	 * Adds an item to this priority queue.
	 * (Runs in logarithmic time.) Can sometimes terminate early.
	 * 
	 * WARNING: make sure you use the compare method defined for you below
	 * 
	 * @param x
	 *            -- the item to be inserted
	 */
	public void add( Type x )
	{
		// FILL IN
		// if the array is full, double its capacity
		// add the new item to the next available node in the tree, so that

		// complete tree structure is maintained
		// update size
		// percolate the new item up the levels of the tree until heap order is restored

		// It is STRONGLY recommended that you write a percolateUp helper method!
	}

	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile( String filename )
	{
		try (PrintWriter out = new PrintWriter(filename))
		{
			out.println(this);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}

	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by the
	 * user at construction time, or Comparable, if no Comparator was
	 * provided.
	 */
	private int compare( Type lhs, Type rhs )
	{
		if (comparator == null)
		{
			return ((Comparable<? super Type>) lhs).compareTo(rhs); // safe to ignore warning
		}

		// We won't test your code on non-Comparable types if we didn't supply a Comparator
		return comparator.compare(lhs, rhs);
	}

	/**
	 * @return a copy of the array used in the heap
	 */
	public Object[] toArray()
	{
		Object[] copy_of_array = new Object[size];

		for (int i = 1; i <= size; i++)
		{
			copy_of_array[i] = heap_array[i];
		}

		return copy_of_array;
	}

	/**
	 * @return a string representing the DOT data of the heap 
	 * 
	 * This can be further augmented to print out any instrumented values that you think
	 * are important.  Note: To allow them not to conflict with the DOT notation, simply
	 * preface them with the // comment characters: e.g., "// numbers of insertions: 1234"
	 */
	@Override
	public String toString()
	{
		String result = "digraph Heap {\n\tnode [shape=record]\n";
		for (int i = 1; i <= size; i++)
		{
			result += "\tnode" + i + " [label = \"<f0> |<f1> " + heap_array[i] + "|<f2> \"]\n";
			if (((i * 2)) <= size) result += "\tnode" + i + ":f0 -> node" + ((i * 2)) + ":f1\n";
			if (((i * 2) + 1) <= size) result += "\tnode" + i + ":f2 -> node" + ((i * 2) + 1) + ":f1\n";
		}
		result += "}";

		result += "\n//--------------------------------------------\n" + "// Any other info you want to put about your heap";

		return result;
	}

	/**
	 * 1) copy data from array into heap storage
	 * 2) do an "in place" creation of the heap
	 * 
	 * @param array
	 *            - random data (unordered)
	 */
	public void build_heap_from_array( Type[] array )
	{
		// WARNING: advanced work only worth 2.5% of grade
		// If you do not fully implement this code, leave it blank
	}

	/**
	 * convert the heap array into a sorted array from largest to smallest
	 * 
	 * Note: this destroys the heap property of the array and should be a terminal operation, which
	 *       is not what we would likely do in a real program, but is appropriate to for our purposes (i.e.,
	 *       understanding how heap sort works in place).
	 * 
	 */
	public void heap_sort()
	{
		// WARNING: advanced work only worth 2.5% of grade
		// If you do not fully implement this code, leave it blank

	}

}