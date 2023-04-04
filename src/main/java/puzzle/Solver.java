// package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // A* search. Now, we describe a solution to the 8-puzzle problem
    // that illustrates a general artificial intelligence methodology known as the A* search algorithm.
    // We define a search node of the game to be a board, the number of moves made to reach the board,
    // and the previous search node. First, insert the initial search node (the initial board, 0 moves,
    // and a null previous search node) into a priority queue. Then, delete from the priority queue
    // the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes
    // (those that can be reached in one move from the dequeued search node). Repeat this procedure until
    // the search node dequeued corresponds to the goal board.

    // The efficacy of this approach hinges on the choice of priority function for a search node.
    // We consider two priority functions:
    // The Hamming priority function is the Hamming distance of a board plus the number of moves made
    // so far to get to the search node. Intuitively, a search node with a small number of tiles in
    // the wrong position is close to the goal, and we prefer a search node if has been reached using
    // a small number of moves.
    // The Manhattan priority function is the Manhattan distance of a board plus the number of moves made
    // so far to get to the search node.
    // To solve the puzzle from a given search node on the priority queue, the total number of moves we
    // need to make (including those already made) is at least its priority, using either the Hamming
    // or Manhattan priority function. Why? Consequently, when the goal board is dequeued, we have
    // discovered not only a sequence of moves from the initial board to the goal board, but one that
    // makes the fewest moves. (Challenge for the mathematically inclined: prove this fact.)

    // find a solution to the initial board (using the A* algorithm)

    // -------------------------The critical optimization--------------------------------------
    // A* search has one annoying feature: search nodes corresponding
    // to the same board are enqueued on the priority queue many times (e.g., the bottom-left
    // search node in the game-tree diagram above). To reduce unnecessary exploration of useless
    // search nodes, when considering the neighbors of a search node, don’t enqueue a neighbor if
    // its board is the same as the board of the previous search node in the game tree.

    // ---------------------Caching the Hamming and Manhattan priorities-------------------------
    // To avoid recomputing the Manhattan priority of a search node from scratch each time during
    // various priority queue operations, pre-compute its value when you construct the search node;
    // save it in an instance variable; and return the saved value as needed. This caching technique
    // is broadly applicable: consider using it in any situation where you are recomputing the same
    // quantity many times and for which computing that quantity is a bottleneck operation.

    // To implement the A* algorithm, you must use the MinPQ data type for the priority queue.

    // --------------------------------Detecting unsolvable boards---------------------------------
    // Not all initial boards can lead to the goal board by a sequence of moves.
    // To detect such situations, use the fact that boards are divided into two equivalence
    // classes with respect to reachability:
    //      Those that can lead to the goal board
    //      Those that can lead to the goal board if we modify the initial board by
    //      swapping any pair of tiles (the blank square is not a tile).
    // (Difficult challenge for the mathematically inclined: prove this fact.) To apply the fact,
    // run the A* algorithm on two puzzle instances—one with the initial board and one with the initial
    // board modified by swapping a pair of tiles—in lockstep (alternating back and forth between exploring
    // search nodes in each of the two game trees). Exactly one of the two will lead to the goal board.
    private Board board;
    private Solver parent;
    private Solver smaller;
    private Solver larger;
    private Iterable<Board> solution;

    public Solver(Board initial){
        this(initial, null, null);
        MinPQ<Board> node = new MinPQ<Board>();

    }


    private  Solver(Board initial, Solver smaller, Solver larger){
        this.board = initial;
        this.smaller = smaller;
        this.larger = larger;
        if(smaller != null){
            this.parent = smaller;
        }
        if (larger != null){
            this.parent = larger;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return moves() != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return board.manhattan();

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        Queue<Board> boards = new Queue<>();
        if (!isSolvable()){
            solution = null;
        } else {
            solution = boards;
        }

        return solution;
    }





    // test client (see below)
    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

 /*   Corner cases.

    Throw an IllegalArgumentException in the constructor if the argument is null.
    Return -1 in moves() if the board is unsolvable.
    Return null in solution() if the board is unsolvable.*/
}
