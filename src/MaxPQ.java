import java.util.Comparator;

public class MaxPQ implements PriorityQueue {
    // priority queue
    // heap tree implementation
    // parent->

    private Object[] heap; // the heap to store data in
    private int size; // current size of the queue
    private static final int DEFAULT_CAPACITY = 4; // default capacity
    private static final int AUTOGROW_SIZE = 4; // default auto grow
    private Comparator comparator; // the comparator to use between the objects

    public MaxPQ(Comparator comparator) {
        this.heap = new Object[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
    }

    public MaxPQ(Object[] heap, int size, Comparator comparator) {
        this.heap = heap;
        this.size = size;
        this.comparator = comparator;
    }

    /**
     * Inserts the specified element into this priority queue.
     *
     * @param item
     */
    @Override
    public void add(Object item) {
        // Check available space
        if (size == heap.length - 1)
            grow();

        // Place item at the next available position
        // value ++size will be changed before the evaluation of the expression
        heap[++size] = item;

        // Let the newly added item swim
        // size -> index of the last element
        swim(size);
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    @Override
    public Object peek() {
        // Ensure not empty
        if (size == 0)
            return null;

        // return root without removing
        return heap[1];
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    @Override
    public Object getMax() {
        // Ensure not empty
        if (size == 0)
            return null;

        // Keep a reference to the root item
        Object root = heap[1];

        // Replace root item with the one at rightmost leaf
        heap[1] = heap[size];
        size--;

        // Dispose the rightmost leaf
        // Sink the new root element
        sink(1);

        // Return the int removed
        return root;
    }

    /**
     * Helper function to swim items to the top
     *
     * @param i the index of the item to which operation "swim" is applied
     */
    // Bottom-up reheapify / swim //
    private void swim(int i) {
        // if i is root (i==1) return
        if (i == 1)
            return;

        // find parent
        int parent = i / 2;

        // compare parent with child i
        while (i != 1 && comparator.compare(heap[i], heap[parent]) > 0) {
            swap(i, parent);
            i = parent;
            parent = i / 2;
        }

    }

    /**
     * Helper function to swim items to the bottom
     *
     * @param i the index of the item to which operation "sink" is applied
     */
    // Top-down reheapify / sink //
    private void sink(int i) {
        // determine left, right child
        int left = 2 * i;
        int right = left + 1;

        // if 2*i > size, node i is a leaf return
        if (left > size)
            return;

        // while haven't reached the leafs
        while (left <= size) {
            // Determine the largest child of node i
            int max = left;
            if (right <= size) {
                if (comparator.compare(heap[left], heap[right]) < 0)
                    max = right;
            }

            // If the heap condition holds, stop. Else swap and go on.
            // child smaller than parent
            if (comparator.compare(heap[i], heap[max]) >= 0)
                return;
            else {
                swap(i, max);
                i = max;
                left = i * 2;
                right = left + 1;
            }
        }
    }

    /**
     * Helper function to swap two elements in the heap
     *
     * @param i the first element's index
     * @param j the second element's index
     */
    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * Helper function to grow the size of the heap
     */
    private void grow() {
        Object[] newHeap = new Object[heap.length + AUTOGROW_SIZE];

        // copy array
        //(notice: in the priority queue, elements are located in the array slots with positions in [1, size])
        for (int i = 0; i <= size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

    public Object[] getHeap() {
        return heap;
    }

    public int getSize() {
        return size;
    }
}

