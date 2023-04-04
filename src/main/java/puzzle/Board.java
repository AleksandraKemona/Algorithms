// package puzzle;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int n;

    private Board board;
    private ArrayList<Integer> boardArray;
    private ArrayList<Integer> goalArray;
    private Iterable<Board> neighbourBoards;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    // Constructor.  You may assume that the constructor receives an n-by-n array
    // containing the n2 integers between 0 and n2 − 1, where 0 represents the blank square.
    // You may also assume that 2 ≤ n < 128.
    public Board(int[][] tiles) {

        this.n = tiles.length;
        this.boardArray = arrayToArrayList(tiles);
    }

    // string representation of this board
    // String representation.  The toString() method returns a string composed of n + 1 lines.
    // The first line contains the board size n; the remaining n lines contains
    // the n-by-n grid of tiles in row-major order, using 0 to designate the blank square.
    public String toString(){
        int size = dimension();
        String boardSize = size + "\n";
        String boardString = "";
        StringBuilder boardBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String line = "";
            StringBuilder lineBuilder = new StringBuilder();
            for (int j = 0; j < n; j++) {
                int number = boardArray.get(i * n + j);
                lineBuilder.append(" " + number + " ");
            }
            lineBuilder.append("\n");
            line = lineBuilder.toString();
            boardBuilder.append(line);
        }
        boardString = boardBuilder.toString();
        return boardSize + boardString;

    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    // Hamming and Manhattan distances.
    // To measure how close a board is to the goal board, we define two notions of distance.
    // The Hamming distance betweeen a board and the goal board is the number of tiles in the wrong position.
    // The Manhattan distance between a board and the goal board is the sum of
    // the Manhattan distances (sum of the vertical and horizontal distance) from the tiles to their goal positions.
    public int hamming() {
        int wrongTilesNumber = 0;
        for (int i = 0; i < n*n; i++) {
            if (boardArray.get(i) != (i + 1) && boardArray.get(i) != 0) {
                    wrongTilesNumber++;
            }
        }
        return wrongTilesNumber;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int counter = 0;
        for (int i = 1; i < n*n; i++) {
            int boardIndex = boardArray.indexOf(i);
            int boardX = boardIndex % n;
            int boardY = boardIndex / n;
            createGoalBoard();
            int goalIndex = goalArray.indexOf(i);
            int goalX = goalIndex % n;
            int goalY = goalIndex / n;
            if (boardIndex != goalIndex) {
                int vertDist = Math.abs(boardX - goalX);
                int horizDist = Math.abs(boardY - goalY);
                counter = counter + vertDist + horizDist;
            }
        }
        return counter;
    }

    private void createGoalBoard() {
        goalArray = new ArrayList<>();
        for (int i = 0; i < (n*n); i++) {
            goalArray.add(i, i+1);
        }
        goalArray.add((n*n-1), 0);
    }

    // is this board the goal board?
    public boolean isGoal() {
        goalArray = new ArrayList<>();
        createGoalBoard();
        for (int i = 0; i < n * n; i++) {
            if (boardArray.get(i) != goalArray.get(i)) return false;
            }
        return true;
        }




    // Comparing two boards for equality.  Two boards are equal if they are have the same size and their
    // corresponding tiles are in the same positions. The equals() method is inherited from java.lang.Object,
    // so it must obey all of Java’s requirements.
    // does this board equal y?
    public boolean equals(Object y){
       if (y == this) return true;
       if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < boardArray.size(); i ++) {
            if (this.boardArray.get(i) != that.boardArray.get(i))
                return false;
        }
        return true;
    }

    // Neighboring boards.  The neighbors()
    // method returns an iterable containing the neighbors of the board.
    // Depending on the location of the blank square, a board can have 2, 3, or 4 neighbors.
    // all neighboring boards
    public Iterable<Board> neighbors(){
        Queue<Board> neighbors = new Queue<>();
        int[][] startingArray = arrayListToArray(boardArray);
        final ArrayList<Integer> firstBoard = arrayToArrayList(startingArray);

        int blankPlate = firstBoard.indexOf(0);
        int col = blankPlate % n;
        int row = blankPlate / n;

        int[] positions = {row - 1, row + 1, col + 1, col -1};
        int[] moves = {-1, 1, 1, -1};
        // for (int i = 0; i < 4; i++) {
        //
        //     int position = positions[i];
        //     int move = moves[i];
        //     if (position >= 0 && position < n){
        //         int index = blankPlate + move;
        //         oneNeighbour.set(blankPlate, boardArray.get(index));
        //         oneNeighbour.set(index, 0);
        //         neighbors.enqueue(new Board(arrayListToArray(oneNeighbour)));
        //     }
        // }
        if(positions[0] >=0 && positions[0] <n){
            int[][] neighbour0 = copyArray(startingArray);
            neighbour0[row][col] = startingArray[row + moves[0]][col];
            neighbour0[row + moves[0]][col] = 0;
            ArrayList<Integer> oneNeighbour0 = arrayToArrayList(neighbour0);
            neighbors.enqueue(new Board(arrayListToArray(oneNeighbour0)));
        }
        if(positions[1] >=0 && positions[1] <n){
            int[][] neighbour1 = copyArray(startingArray);
            neighbour1[row][col] = startingArray[row + moves[1]][col];
            neighbour1[row + moves[1]][col] = 0;
            ArrayList<Integer> oneNeighbour1 = arrayToArrayList(neighbour1);
            neighbors.enqueue(new Board(arrayListToArray(oneNeighbour1)));
        }
        if(positions[2] >=0 && positions[2] <n) {
            int[][] neighbour2 = copyArray(startingArray);
            neighbour2[row][col] = startingArray[row][col+ moves[2]];
            neighbour2[row][col + moves[2]] = 0;
            ArrayList<Integer> oneNeighbour2 = arrayToArrayList(neighbour2);
            neighbors.enqueue(new Board(arrayListToArray(oneNeighbour2)));
        }
        if(positions[3] >=0 && positions[3] <n) {
            int[][] neighbour3 = copyArray(startingArray);
            neighbour3[row][col] = startingArray[row][col + moves[3]];
            neighbour3[row][col + moves[3]] = 0;
            ArrayList<Integer> oneNeighbour3 = arrayToArrayList(neighbour3);
            neighbors.enqueue(new Board(arrayListToArray(oneNeighbour3)));
        }
        return neighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinBoard = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                twinBoard[i][j] = boardArray.get(i * n + j);
            }
        }
        int random1x = StdRandom.uniformInt(0, boardArray.size());
        int random2x = StdRandom.uniformInt(0, boardArray.size());
        int random1y = StdRandom.uniformInt(0, boardArray.size());
        int random2y = StdRandom.uniformInt(0, boardArray.size());
        twinBoard[random1x][random1y] = boardArray.get(random2x*random2y);
        twinBoard[random2x][random2y] = boardArray.get(random1x*random1y);
        return new Board(twinBoard);
    }

    private ArrayList<Integer> arrayToArrayList(int[][] tiles) {
        ArrayList<Integer> listOfTiles = new ArrayList<>();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                listOfTiles.add(tiles[i][j]);
            }
        }
        return listOfTiles;
    }

    private int[][] arrayListToArray (ArrayList<Integer> list) {
        int[][] arrayOfTiles = new int[n][n];
        for (int i = 0; i < (n*n); i++){
            int row = i / n;
            int col = i % n;
            arrayOfTiles[row][col] = list.get(i);
        }
        return arrayOfTiles;
    }

    private  int[][]copyArray(int[][] oldArray) {
        int[][] newArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newArray[i][j] = oldArray[i][j];
            }
        }
        return newArray;
    }

    // unit testing (not graded)
    // public static void main(String[] args){
    //     String grid = StdIn.readString();
    //     int sizeOfBoard = grid.charAt(0);
    //     int[][] points = new int[sizeOfBoard][sizeOfBoard];
    //     for (int i = 0; i < sizeOfBoard; i++){
    //         String oneLine = StdIn.readString();
    //         for (int j = 0; j < sizeOfBoard; j++){
    //             points[i][j] = oneLine.charAt(j);
    //         }
    //     }
    //     new Board(points);
    // }
}
