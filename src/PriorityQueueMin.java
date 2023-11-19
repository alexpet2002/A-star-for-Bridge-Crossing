public interface PriorityQueueMin {
    /**
     * Inserts the specified element into this priority queue.
     *
     * @param item
     */
    void add(Object item);


    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    Object peek();


    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    Object getMin();
}



