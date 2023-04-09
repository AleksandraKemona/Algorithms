// package puzzle;

import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;
public class Board {

    private int n;
    private char[] goalArray;
    private char[] board1D;
    private int length1D;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        // this.boardArray = arrayToArrayList(tiles);
        board1D = changeTo1dim(tiles);
        length1D = n * n;
    }

    // string representation of this board
    public String toString() {
        StringBuilder boardBuilder = new StringBuilder();
        boardBuilder.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int element = board1D[i * n + j];
                boardBuilder.append(" " + element + " ");
            }
            boardBuilder.append("\n");
        }
        return boardBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place Hamming distance
    public int hamming() {
        int wrongTilesNumber = 0;
        for (int i = 0; i < n * n; i++) {
            if (board1D[i] != (i + 1) && board1D[i] != 0) {
                wrongTilesNumber++;
            }
        }
        return wrongTilesNumber;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int counter = 0;
        createGoalBoard();
        for (int i = 1; i < length1D; i++) {
            int boardIndex = searchIndex(board1D, i);
            int goalIndex = searchIndex(goalArray, i);

            if (boardIndex != goalIndex) {
                int dist = Math.abs(goalIndex - boardIndex);
                int horizDist = 0;
                int vertDist = Math.abs((boardIndex / n) - (goalIndex / n));
                horizDist = Math.abs(dist - n * (vertDist));
                counter = counter + vertDist + horizDist;
            }
        }
        return counter;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (board1D[length1D - 1] != 0) {
            return false;
        } else {
            for (int i = 0; i < n * n - 2; i++) {
                if (board1D[i] != (i + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < board1D.length; i++) {
            if (this.board1D[i] != that.board1D[i])
                return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbours = new Queue<>();
        char[] startingArray = Arrays.copyOf(board1D, length1D);

        int blankIndex = searchIndex(startingArray, 0);
        int[] moves = {1, -1, n, -n};
        for (int move: moves) {
            int newIndex = blankIndex + move;
            if (checkBoundaries(newIndex, move)) {
                char[] neighbour = Arrays.copyOf(startingArray, length1D);
                neighbour[blankIndex] = startingArray[newIndex];
                neighbour[newIndex] = 0;
                neighbours.enqueue(new Board(changeTo2dim(neighbour)));
            }
        }
        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        char[] startingArray = Arrays.copyOf(board1D, length1D);
        char[] twinArray = Arrays.copyOf(startingArray, length1D);
        int index = 0;
        if (startingArray[index] == 0 || startingArray[index + 1] == 0) {
            index = index + n;
        }
        twinArray[index] = startingArray[index + 1];
        twinArray[index + 1] = startingArray[index];
        return new Board(changeTo2dim(twinArray));
    }

    // Helper methods
    private int searchIndex(char[] elementsArray, int element) {
        int counter = 0;
        while (elementsArray[counter] != element) {
            counter++;
        }
        return counter;
    }
    private void createGoalBoard() {
        goalArray = new char[length1D];
        for (int i = 0; i < (length1D); i++) {
            goalArray[i] = (char) (i+1);
        }
        goalArray[length1D - 1] = 0;
    }
    private boolean checkBoundaries(int index, int move) {
        if (move == 1 && (index % n) < (n) && (index % n) != 0) return true;
        if (move == -1 && (index % n) >= 0 && (index % n) != n-1) return true;
        if (move == n && index < length1D) return true;
        return move == -n && index >= 0;
    }
    private char[] changeTo1dim(int[][] array2D) {
        char[] array1D = new char[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array1D[i * n + j] = (char) array2D[i][j];
            }
        }
        return array1D;
    }

    private int[][] changeTo2dim(char[] array1D) {
        int[][] array2D = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array2D[i][j] = array1D[i * n + j];
            }
        }
        return array2D;
    }

    // unit testing (not graded)
    // public static void main(String[] args){
    //
    //     int sizeOfBoard = 3;
    //     int[][] points = new int[sizeOfBoard][sizeOfBoard];
    //     points[0][0] = 0;
    //     points[0][1] = 1;
    //     points[0][2] = 3;
    //     points[1][0] = 4;
    //     points[1][1] = 2;
    //     points[1][2] = 5;
    //     points[2][0] = 7;
    //     points[2][1] = 8;
    //     points[2][2] = 6;
    //     new Board(points);
    // }
}
