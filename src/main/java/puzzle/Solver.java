// package puzzle;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
// import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
// import java.util.Arrays;


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
    // private Board board;
    private boolean solvable;

    private boolean solved = false;
    private boolean twinSolved = false;


    // private boolean solvable;
    private int minMoves;
    // private Iterable<Board> solution;

    private Node minNode;


    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int moves = 0;
        int twinMoves = 0;

        MinPQ<Node> nodes = new MinPQ<>();
        // MinPQ<Node> twinNodes = new MinPQ<>();

        Node searchNode = new Node(initial);
        nodes.insert(searchNode);

        // Node twinSearchNode = new Node(searchNode.getBoard().twin());
        // twinNodes.insert(twinSearchNode);


        Node removedNode;
        // Node removedTwinNode;

        // while (!solved && !twinSolved) {
            while (!solved) {

            removedNode = nodes.delMin();
            // removedTwinNode = twinNodes.delMin();

            Board currentBoard = removedNode.getBoard();
            // Board currentTwinBoard = removedTwinNode.getBoard();


            if (currentBoard.isGoal()) solved = true;
            // if (currentTwinBoard.isGoal()) twinSolved = true;

            Node neighbourNode = getNeighbour(removedNode);
            nodes.insert(neighbourNode);

            // Node twinNeighbourNode = getNeighbour(removedTwinNode);
            // twinNodes.insert(twinNeighbourNode);


            moves = removedNode.getMoves() + 1;

            int n = initial.dimension();
            int movesLimit = StdRandom.uniformInt((n * n * n), (n * n * n + 4));
            if (moves >= movesLimit) {
                while (!twinSolved) {
                    MinPQ<Node> twinNodes = new MinPQ<>();
                    Node twinSearchNode = new Node(removedNode.getBoard().twin());
                    twinNodes.insert(twinSearchNode);
                    Node removedTwinNode;
                    removedTwinNode = twinNodes.delMin();
                    Board currentTwinBoard = removedTwinNode.getBoard();
                    if (currentTwinBoard.isGoal()) twinSolved = true;
                    Node twinNeighbourNode = getNeighbour(removedTwinNode);
                    twinNodes.insert(twinNeighbourNode);
                    twinMoves = removedTwinNode.getMoves() + 1;
                    if (twinMoves >= movesLimit){
                        twinSolved = true;
                    }
                }
                solved = true;
            }
            minNode = removedNode;
        }

        solvable = !twinSolved;
        minMoves = moves - 1;
    }

    // public Solver(Board initial) {
    //     if (initial == null)
    //         throw new java.lang.IllegalArgumentException();
    //     //minMoves = initial.manhattan();
    //     int moves = 0;
    //     int twinMoves = 0;
    //
    //     Queue<Board> neighbors;
    //     Queue<Board> twinNeighbors = new Queue<Board>();
    //
    //     MinPQ<Node> searchNodes = new MinPQ<Node>();
    //     MinPQ<Node> twinNodes = new MinPQ<Node>();
    //
    //     Node searchNode = new Node(initial, moves, null);
    //     Node twinSearchNode = new Node(initial.twin(), twinMoves, null);
    //
    //     twinNodes.insert(twinSearchNode);
    //     searchNodes.insert(searchNode);
    //
    //     boolean solved = false;
    //     boolean twinSolved = false;
    //     // System.out.println("Get Board: " + searchNodes.delMin().getBoard());
    //     Node current = null;
    //
    //     while (!solved) {
    //         current = searchNodes.delMin();
    //         Node twinCurrent = twinNodes.delMin();
    //         Node twinPredecessor = twinCurrent.getPredecessor();
    //         Board twinTemp = twinCurrent.getBoard();
    //         twinSolved = twinTemp.isGoal();
    //
    //         // System.out.println("current before get Neighbour: \n" + current);
    //
    //             Node neighbourNode = getNeighbour(current);
    //             // System.out.println("neighbour node: \n " + neighborNode.getBoard());
    //             solved = neighbourNode.getBoard().isGoal();
    //         // System.out.println("serch nodes 1: " + searchNodes.size());
    //         // System.out.println("Neighbour node before insert to serch \n" + neighbourNode.getBoard());
    //             searchNodes.insert(neighbourNode);
    //         // System.out.println("serch nodes: " + searchNodes.size());
    //
    //
    //
    //         for (Board b : twinTemp.neighbors())
    //             twinNeighbors.enqueue(b);
    //
    //
    //
    //
    //         while(twinNeighbors.size() > 0) {
    //             Board board = twinNeighbors.dequeue();
    //
    //             int twinMove = current.getMoves();
    //             twinMove++;
    //             if (twinPredecessor != null && twinPredecessor.getBoard().equals(board))
    //                 continue;
    //
    //             Node neighborTwinNode = new Node(board, twinMove, twinCurrent);
    //             twinNodes.insert(neighborTwinNode);
    //         }
    //
    //         moves = current.getMoves() + 1;
    //         twinMoves = twinCurrent.getMoves() + 1;
    //         minNode = current;
    //     }
    //
    //     solvable = !twinSolved;
    //     minMoves = moves - 1;
    // }

    // private void printBoard(Board board){
    //     int n = 3;
    //     int[][] testBoard =
    //     for (int x = 0; x < n; x++){
    //         for (int j = 0; j < n; j++){
    //             System.out.print(testBoard[x][j] +" ");
    //         }
    //         System.out.println();
    //     }
    // }

    // test client (see below)
    public static void main(String[] args) {
        // ArrayList<Integer> list = new ArrayList<>();
        // list.add(2);
        // list.add(3);
        // list.add(5);
        // list.add(1);
        // list.add(0);
        // list.add(4);
        // list.add(7);
        // list.add(8);
        // list.add(6);
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        // int n = 3;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                // tiles[i][j] = list.get(i * n + j);
                tiles[i][j] = in.readInt();
            }
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

    private Node getNeighbour(Node currentNode) {
        ArrayList<Board> listOfNeighbours = new ArrayList<>();
        Board centerBoard = currentNode.getBoard();
        // System.out.println("center board " + centerBoard);
        Node predecessor = currentNode.getPredecessor();
        int moves = currentNode.getMoves();
        // System.out.println("centerboard " + centerBoard);
        for (Board b : centerBoard.neighbors()) {
            // System.out.println("for loop");
            if (predecessor != null && !predecessor.getBoard().equals(b)) {
                listOfNeighbours.add(b);
            } else if (predecessor == null) {
                // System.out.println("second if ");
                listOfNeighbours.add(b);
                // } else if (predecessor.getBoard().equals(b)){
                //     // System.out.println("board already used before");
            }
        }

        Board[] neighbours = new Board[listOfNeighbours.size()];
        // System.out.println("is neighbours created? " + neighbours.length);
        for (int i = 0; i < listOfNeighbours.size(); i++) {
            neighbours[i] = listOfNeighbours.get(i);
            // System.out.println("are indexes aded? " + neighbours[i]);
        }
        // int[] sortedPriorities = new int[neighbours.length];
        sort(neighbours);
        // System.out.println("prioritis are sorted? " + sortedPriorities[0] + sortedPriorities[1] + sortedPriorities[2]);
        Board bestBoard = neighbours[0];
        // System.out.println("best board " + neighbours[0]);
        // int currentPriority = (centerBoard.manhattan() * 2);

        // for (int i = 0; i < neighbours.length - 1; i++) {
        //     Board board = neighbours[i];
        //     Board nextboard = neighbours[i + 1];
        //     if (board.manhattan() < nextboard.manhattan()) {
        //         bestBoard = board;
        //         currentPriority = board.manhattan();
        // } if (board.manhattan() == nextboard.manhattan() && board.){
        //
        // }

        // if (board != null && !usedBoards.contains(board)){
        // if (board != null) {
        // int priority = board.manhattan();
        // if (priority > currentPriority) {
        // System.out.println("inside priority loop");
        // bestBoard = board;
        // usedBoards.add(bestBoard);
        // currentPriority = priority;
        // } else if (priority == currentPriority) {

        // }

        // }

        moves++;
        // System.out.println("current node moves: " + currentNode.getMoves());
        Node bestNode = new Node(bestBoard, moves, currentNode);

        // System.out.println("Current priority " + currentPriority);
        return bestNode;
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        // System.out.println("Metoda is solvable " + solvable);
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return minMoves;
        } else {
            return -1;
        }


    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> boards = new Stack<>();
        Node minNode = this.minNode;
        if (this.isSolvable()) {
            while (minNode.getPredecessor() != null) {
                boards.push(minNode.getBoard());
                minNode = minNode.getPredecessor();
            }
            boards.push(minNode.getBoard());
            return boards;
        } else {
            return null;
        }
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private Node parent;
        private Node smaller;
        private Node larger;

        private Node predecessor;
        private int moves;
        private int priority;

        public Node(Board initial) {
            this(initial, 0, null);
        }

        public Node(Board b, int m, Node pred) {
            this.board = b;
            this.moves = m;
            this.predecessor = pred;

            this.priority = m + board.manhattan();
        }

        public Board getBoard() {
            return board;
        }
        // public int getPriority() {
        //     return priority;
        // }

        public int getMoves() {
            return moves;
        }

        // public Node getParent() {
        //     return parent;
        // }

        // public void setParent(Node p) {
        //     parent = p;
        // }

        // public Node getSmaller() {
        //     return smaller;
        // }
        //
        // public Node getLarger() {
        //     return larger;
        // }

        // public boolean isSmaller() {
        //     return getParent() != null && this == getParent().getSmaller();
        // }
        //
        // public boolean isLarger() {
        //     return getParent() != null && this == getParent().getLarger();
        // }

        // public Node minimum() {
        //     Node node = this;
        //     while (node.getSmaller() != null) {
        //         node = node.getSmaller();
        //     }
        //     return node;
        // }
        //
        // public Node maximum() {
        //     Node node = this;
        //     while (node.getLarger() != null) {
        //         node = node.getLarger();
        //     }
        //     return node;
        // }

        // public Node successor() {
        //     if (getLarger() != null) {
        //         return getLarger().minimum();
        //     }
        //     Node node = this;
        //     while (node.isLarger()) {
        //         node = node.getParent();
        //     }
        //     return node.getParent();
        // }

        public Node getPredecessor() {
            return predecessor;
            // if (getSmaller() != null) {
            //     return getSmaller().maximum();
            // }
            // Node node = this;
            // while (node.isSmaller()) {
            //     node = node.getParent();
            // }
            // return node.getParent();
        }

        @Override
        public int compareTo(Node o) {
            return this.priority - o.priority;
        }

        // public int size() {
        //     return size(this);
        // }

        // public boolean equals(Object object) {
        //     if (this == object) {
        //         return true;
        //     }
        //     if (object == null || object.getClass() != getClass()) {
        //         return false;
        //     }
        //     Node other = (Node) object;
        //     return this.board.equals(other.board)
        //             && equalsSmaller(other.getSmaller())
        //             && equalsLarger(other.getLarger());
        // }

        // private int size(Node node) {
        //     if (node == null) {
        //         return 0;
        //     }
        //     return 1 + size(node.getSmaller()) + size(node.getLarger());
        // }

        // private boolean equalsSmaller(Node other) {
        //     return getSmaller() == null && other == null
        //             || getSmaller() != null && getSmaller().equals(other);
        // }

        // private boolean equalsLarger(Node other) {
        //     return getLarger() == null && other == null
        //             || getLarger() != null && getLarger().equals(other);
        // }
    }

    private static void exch(Board[] a, int i, int j) {
        {
            Board swap = a[i];
            a[i] = a[j];
            a[j] = swap;
        }
    }

    private void sort(Board[] neighbours) {
        for (int i = 0; i < neighbours.length - 1; i++) {
            // System.out.println("co z tymi tabelami? " + neighbours[i] + "\n pirority " + neighbours[i].manhattan());
            // System.out.println("priorities: \n i: " + neighbours[i].manhattan() + "j: " + neighbours[j].manhattan());
            int j = i + 1;
            while (j > 0) {
                int priorI = neighbours[j - 1].manhattan();
                int priorJ = neighbours[j].manhattan();
                if (priorJ < priorI) {
                    exch(neighbours, j, j - 1);
                } else {
                    break;
                }
                j--;
            }
        }
    }






 /*   Corner cases.

    Throw an IllegalArgumentException in the constructor if the argument is null.
    Return -1 in moves() if the board is unsolvable.
    Return null in solution() if the board is unsolvable.*/
}
