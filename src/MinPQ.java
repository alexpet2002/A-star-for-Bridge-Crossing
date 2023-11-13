import java.util.Comparator;

public class MinPQ {
    private Object[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 4;
    private static final int AUTOGROW_SIZE = 4;
    private Comparator comparator;

    public MinPQ(Comparator comparator) {
        this.heap = new Object[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
    }

    public MinPQ(Object[] heap, int size, Comparator comparator) {
        this.heap = heap;
        this.size = size;
        this.comparator = comparator;
    }


    public void add(Object item) {
        if (size == heap.length - 1)
            grow();

        heap[++size] = item;
        swim(size);
    }


    public Object peek() {
        if (size == 0)
            return null;

        return heap[1];
    }


    public Object getMin() {
        if (size == 0)
            return null;

        Object root = heap[1];
        heap[1] = heap[size];
        size--;
        sink(1);

        return root;
    }

    private void swim(int i) {
        if (i == 1)
            return;

        int parent = i / 2;

        while (i != 1 && comparator.compare(heap[i], heap[parent]) < 0) {
            swap(i, parent);
            i = parent;
            parent = i / 2;
        }
    }

    private void sink(int i) {
        int left = 2 * i;
        int right = left + 1;

        if (left > size)
            return;

        while (left <= size) {
            int min = left;
            if (right <= size) {
                if (comparator.compare(heap[left], heap[right]) > 0)
                    min = right;
            }

            if (comparator.compare(heap[i], heap[min]) <= 0)
                return;
            else {
                swap(i, min);
                i = min;
                left = i * 2;
                right = left + 1;
            }
        }
    }

    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private void grow() {
        Object[] newHeap = new Object[heap.length + AUTOGROW_SIZE];

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

    public static void main(String[] args) {
        MinPQ q = new MinPQ(new IntegerComparator());
        q.add(3);
        q.add(4);
        q.add(5);
        q.add(6);
        System.out.println(q.getMin());
    }
}
