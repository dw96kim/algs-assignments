import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] queue;
    private int N;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        queue = (Item[]) new Object[1];
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) 
            copy[i] = queue[i];
        queue = copy;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return N;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("Cannot add a null item");
        if (N == queue.length)
            resize(2 * queue.length);
        queue[N] = item;
        N++;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("The queue is empty");
        int a = StdRandom.uniform(N);
        Item item = queue[a];
        queue[a] = null;
        for (int i = a + 1; i < N; i++)
            queue[i-1] = queue[i];
        N--;
        if (N > 0 && N == queue.length/4) resize(queue.length/2);
        return item;
    }
    
    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("The queue is empty");
        return queue[StdRandom.uniform(N)];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private int[] temp; // return items queue[temp[0]], queue[temp[1]],..
        private int n = 0; // next item to return is queue[temp[n]]
        
        public RandomIterator() {
            n = 0;
            temp = new int[N];
            for (int i = 0; i < N; i++)
                temp[i] = i;
            StdRandom.shuffle(temp);
        }
        public boolean hasNext() {
            return n < N;
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return queue[temp[n++]];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        StdOut.println("size of RandomizedQueue = " + rq.size());
        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        for (String s : rq) {
            for (String t : rq) {
                StdOut.println(s + ", " + t);
            }
        }
        StdOut.println(rq.dequeue());
    }
}
