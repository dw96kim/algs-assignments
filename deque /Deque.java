import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int N;
    
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("Cannot add a null item");
        //Node node = new Node();
        //node.item = item;
        if (isEmpty()) {
            first = new Node();
            last = new Node();
            first.item = item;
            last = first;
        }
        else {
            /*node.next = first;
            first.prev = node;
            first = node;
            first.prev = null;*/
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.prev = first;
            first.prev = null;
            
        }
        N++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("Cannot add a null item");
        if (isEmpty()) {
            last = new Node();
            first = new Node();
            last.item = item;
            first = last;
        }
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = oldlast;
            oldlast.next = last;
        }
        N++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (N == 0)
            throw new java.util.NoSuchElementException("The queue is empty");
        if (N == 1) {
            Item item = first.item;
            first = null;
            last = first;
            N--;
            return item;
        }
        else {
            Item item = first.item;
            first = first.next;
            first.prev = null;
            N--;
            return item;
        }
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (N == 0)
            throw new java.util.NoSuchElementException("The queue is empty");
        if (N == 1) {
            Item item = last.item;
            last = null;
            first = last;
            N--;
            return item;
        }
        else {
            Item item = last.item;
            last = last.prev;
            last.next = null;
            N--;
            return item;
        }
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> 
    {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        /*StdOut.println(q.isEmpty());
        StdOut.println(q.size());
        q.addFirst("a");
        q.addFirst("b");
        q.addLast("c");*/
        q.addFirst("a");
        q.addFirst("b");
        q.removeLast();
        for (String s : q)
            StdOut.println(s);
        /*for (String s : q) {
            for (String t : q) {
                StdOut.println(s + ", " + t);
            }
        }
        q.removeFirst();
        q.removeLast();
        for (String s : q)
            StdOut.println(s);*/
    }
}