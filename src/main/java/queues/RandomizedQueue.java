/* Throw an IllegalArgumentException if the client calls enqueue() with a null argument.
        Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
        Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
        Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.*/

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private Node node;
    private int count;


    // construct an empty randomized queue
    public RandomizedQueue() {
        count = 0;
        first = null;
        last = null;
    }

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    private Node randomNode() {
        int randomIndex = StdRandom.uniformInt(0, count);
        int currentIndex = 0;
        Node nodeRand = first;
        while (currentIndex != randomIndex) {
            nodeRand = nodeRand.next;
            currentIndex++;
        }
        return nodeRand;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (count == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        count++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0)
            throw new NoSuchElementException("RandomizedQueue is empty");
        Node random = randomNode();
        count --;
        if (random.previous == null) {
            first = random.next;
            if (first != null)
                first.previous = null;
            else
                last = first;
        }
        else if (random.next == null) {
            last = random.previous;
            last.next = null;
        }
        else {
            random.previous.next = random.next;
            random.next.previous = random.previous;
            random.previous = null;
            random.next = null;
        }

        return random.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0)
            throw new java.util.NoSuchElementException("RandomizedQueue is empty");
        Node node = randomNode();
        return node.item;
    }

    // return an independent iterator over items in random order
/*    Each iterator must return the items in uniformly random order.
    The order of two or more iterators to the same randomized queue must be
    mutually independent; each iterator must maintain its own random order.*/
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = randomNode();
        public boolean hasNext() {  return current != null;  }
        public void remove()     { throw new UnsupportedOperationException(); }
        public Item next() {
            if (current == null) { throw new java.util.NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item; }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
