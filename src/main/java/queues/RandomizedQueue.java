
/* Throw an IllegalArgumentException if the client calls enqueue() with a null argument.
        Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
        Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
        Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.*/

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private int count;
    private Item[] array;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        count = 0;
        first = null;
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
        Node node = first;
        while (currentIndex < randomIndex) {
            node = node.next;
            currentIndex++;
        }
        return node;
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
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        count++;
    }

    // remove and return a random item
    public Item dequeue() {
        Node node = randomNode();
        if (node.next != null) {
            node.next.previous = node.previous;
        } else if (node.previous != null) {
            node.previous.next = node.next;
        }
        count--;
        return node.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        Node node = randomNode();
        return node.item;
    }

    // return an independent iterator over items in random order
/*    Each iterator must return the items in uniformly random order.
    The order of two or more iterators to the same randomized queue must be
    mutually independent; each iterator must maintain its own random order.*/
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item>
    {
        private Node current = randomNode();
        public boolean hasNext() {  return current != null;  }
        public void remove()     { throw new UnsupportedOperationException(); }
        public Item next() {
            if (current.next == null) { throw new java.util.NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item; }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
