
/* Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
        Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
        Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
        Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.*/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    // construct an empty deque
    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }
    public Deque() {
        count = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (count == 0);
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (count == 0) {
            first = new Node();
            first.item = item;
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (count == 0) {
            last = new Node();
            last.item = item;
            first = last;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.previous = oldLast;
            oldLast.next = last;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
        throw new java.util.NoSuchElementException();
        }
        Item item = first.item;
        if (count == 1) {
            first = null;
            last =  null;
        } else {
            first.next.previous = null;
            first = first.next;
        }
        count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        if ( last.previous == null){
            last = null;
            first = null;
        } else {
            last.previous.next = null;
            last = last.previous;
        }
        count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() {  return current != null;  }
        public void remove()     { throw new UnsupportedOperationException(); }
        public Item next() {
            if (current.next == null) { throw new java.util.NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item; }
    }

    // unit testing (required)
/*    Unit testing.  Your main() method must call directly every
    public constructor and method to help verify that they work as
    prescribed (e.g., by printing results to standard output).*/
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        // missing type arguments for generic class Deque<Item>
        //   where Item is a type-variable:
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        deque.removeLast();
        deque.removeFirst();

    }

}