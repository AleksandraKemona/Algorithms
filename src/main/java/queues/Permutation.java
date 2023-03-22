

/* Write a client program Permutation.java
        that takes an integer k as a command-line argument;
reads a sequence of strings from standard input using StdIn.readString();
and prints exactly k of them, uniformly at random.
        Print each item from the sequence at most once. */


/* You may assume that 0 ≤ k ≤ n,
        where n is the number of string on standard input. Note that you are not given n.*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> structure = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            structure.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(structure.dequeue());
        }
    }

}

