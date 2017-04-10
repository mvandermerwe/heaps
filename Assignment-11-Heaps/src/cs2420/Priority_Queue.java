package cs2420;

public interface Priority_Queue<Type>
{
	/**
	 * @return the smallest element the queue. 
	 * @throws NoSuchElementException if this priority queue is empty. (Must run in constant time.)
	 */
	public Type peek();

	/**
	 * Removes and returns the smallest element in the queue
	 * @return the min element in the priority queue 
	 */
	public Type dequeue();
	
	/**
	 * Adds an item to this priority queue.
	 */
	public void add(Type x);
	
	/**
	 * return the total number of elements in the queue
	 */
	public int size();
	
	/**
	 * Makes this priority queue empty. 
	 */
	public void clear();

	
}
