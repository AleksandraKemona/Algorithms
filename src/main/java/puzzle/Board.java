// package puzzle;

import edu.princeton.cs.algs4.Queue;
import java.util.ArrayList;


public class Board {

    private int n;

    private Board board;
    private ArrayList<Integer> boardArray;
    private ArrayList<Integer> goalArray;


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
        StringBuilder boardBuilder = new StringBuilder();
        boardBuilder.append( n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                    boardBuilder.append(" " + boardArray.get(i * n + j) + " ");
                }
                boardBuilder.append("\n");
            }
        return boardBuilder.toString();
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
                int horizDist = Math.abs(boardX - goalX);
                int vertDist = Math.abs(boardY - goalY);
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
        goalArray.set((n*n-1), 0);
    }

    // is this board the goal board?
    public boolean isGoal() {
        // return hamming() == 0;
        for (int i = 0; i < n*n - 1; i++) {
            if (boardArray.get(i) != (i + 1)) {
               return false;
            }
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

        int[][] moves = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] move: moves) {
            int newCol = col + move[0];
            int newRow = row + move[1];
            if (isInRange(newRow, newCol)) {
                int[][] neighbour = copyArray(startingArray);
                neighbour[row][col] = startingArray[newRow][newCol];
                neighbour[newRow][newCol] = 0;
                ArrayList<Integer> oneNeighbour0 = arrayToArrayList(neighbour);
                neighbors.enqueue(new Board(arrayListToArray(oneNeighbour0)));
            }
        }
        return neighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] startingArray = arrayListToArray(boardArray);
        ArrayList<Integer> startingList = arrayToArrayList(startingArray);
        Board twinBoard = new Board(copyArray(startingArray));

        int[] twinArray = new int[startingList.size()];
        for (int i = 0; i < startingList.size(); i++){
            twinArray[i] = startingList.get(i);
        }

        for (int i = 0; i < startingList.size(); i++){

            if (twinArray[i] !=0){
                int col = i % n;
                int row = i / n;
                int[][] moves = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};
                for (int[] move: moves) {
                    int newCol = col+move[0];
                    int newRow = row+move[1];
                    if(isInRange(newRow, newCol)){
                        int[][] testBoard = copyArray(startingArray);
                        if (testBoard[newRow][newCol] !=0 ){
                            int temp = testBoard[row][col];
                            testBoard[row][col] = testBoard[newRow][newCol];
                            testBoard[newRow][newCol] = temp;
                            twinBoard = new Board(testBoard);
                        }
                    }
                }
            }
        }
        return twinBoard;


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
    private boolean isInRange(int col, int row){
        return (col >=0 && col <n && row >=0 && row <n);
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
