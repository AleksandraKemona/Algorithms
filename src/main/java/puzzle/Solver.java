package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean solvable;
    private boolean solved = false;
    private boolean twinSolved = false;
    private int minMoves;
    private Node minNode;

    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException();

        int moves = 0;
        Node removedNode;
        Node removedTwinNode;

        MinPQ<Node> nodes = new MinPQ<>();
        Node searchNode = new Node(initial);
        nodes.insert(searchNode);

        MinPQ<Node> twinNodes = new MinPQ<>();
        Node twinSearchNode = new Node(searchNode.board.twin());
        twinNodes.insert(twinSearchNode);


        while (!solved && !twinSolved) {

            removedNode = nodes.delMin();
            removedTwinNode = twinNodes.delMin();

            Board currentBoard = removedNode.board;
            Board currentTwinBoard = removedTwinNode.board;

            if (currentBoard.isGoal()) solved = true;
            if (currentTwinBoard.isGoal()) twinSolved = true;

            for (Board neighbour : removedNode.board.neighbors()) {
                if (removedNode.moves < 1 || !neighbour.equals(removedNode.predecessor.board))
                    nodes.insert(new Node(neighbour, removedNode));
            }

            for (Board neighbour : removedTwinNode.board.neighbors()) {
                if (removedTwinNode.moves < 1 || !neighbour.equals(removedTwinNode.predecessor.board))
                    twinNodes.insert(new Node(neighbour, removedTwinNode));
            }
            moves = removedNode.moves + 1;
            minNode = removedNode;
        }
        minMoves = moves;
        solvable = !twinSolved;
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private Node predecessor;
        private int moves;
        private int priority;
        int man;

        public Node(Board initial) {
            this.board = initial;
            this.moves = 0;
            this.man = board.manhattan();
            this.priority = man + moves;
        }

        public Node(Board b, Node pred) {
            this.board = b;
            this.predecessor = pred;
            this.moves = pred.moves + 1;
            this.man = board.manhattan();
            this.priority = man + moves;
        }

        @Override
        public int compareTo(Node node) {
            if (this.priority - node.priority == 0) {
                return (this.man - node.man);
            } else {
                return (this.priority - node.priority);

            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return minMoves - 1;
        } else {
            return -1;
        }
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> boards = new Stack<>();
        Node lastNode = this.minNode;
        if (this.isSolvable()) {
            while (lastNode.predecessor != null) {
                boards.push(lastNode.board);
                lastNode = lastNode.predecessor;
            }
            boards.push(lastNode.board);
            return boards;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
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

}
