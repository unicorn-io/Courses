import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;
    private int numberOfItems;

    private class Node {
        Item item;
        Node next;

        public Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        numberOfItems = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numberOfItems == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numberOfItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Bad Input");
        }
        Node newNode = new Node(item);
        if (!this.isEmpty()) {
            newNode.next = first;
        }
        first = newNode;
        numberOfItems++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int n = StdRandom.uniform(numberOfItems);
        if (n == 0) {
            Node temp = first;
            Item toReturn = first.item;
            first.item = null;
            try {
                first = first.next;
            } catch (NullPointerException ne) {
                first = null;
            }
            temp.next = null;
            numberOfItems--;
            return toReturn;
        }
        Node temp = first;
        while (n > 1) {
            temp = temp.next;
            n--;
        }
        Node toRemove = temp.next;
        Item toReturn = toRemove.item;
        temp.next = toRemove.next;
        toRemove.item = null;
        toRemove.next = null;
        numberOfItems--;
        return toReturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int n = StdRandom.uniform(numberOfItems);
        Node temp = first;
        while (n-1 >= 0) {
            temp = temp.next;
            n--;
        }
        return temp.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private final Item[] toIterateOver;
        private int currentIndex = 0;

        public RandomizedIterator() {
            toIterateOver = (Item[]) new Object[numberOfItems];
            Node temp = first;
            int a = 0;
            while (temp != null) {
                toIterateOver[a++] = temp.item;
                temp = temp.next;
            }
            StdRandom.shuffle(toIterateOver);
        }

        public void remove() { throw new UnsupportedOperationException("Cha!!"); }

        public boolean hasNext() {
            return currentIndex <= numberOfItems-1;
        }

        public Item next() {
            if (isEmpty() || currentIndex >= numberOfItems) {
                throw new NoSuchElementException("Either the queue is empty or no more elements exist");
            }
            currentIndex++;
            return this.toIterateOver[currentIndex-1];
        }
    }

    public static void main(String [] args) {
        int x = 3;
        RandomizedQueue<String> test = new RandomizedQueue<String>();
        while (x > 0) {
            test.enqueue(StdIn.readString());
            x--;
        }
        x = 3;
        while (x > 0) {
            System.out.println(test.dequeue());
            x--;
        }
    }

}