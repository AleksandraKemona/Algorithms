import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF gridConnection;
    private boolean[] grid;
    private final int top;
    private int bottom;
    private final int n;
    private int openCellCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException();
        gridConnection = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n*n+2];
        top=0;
        bottom=n * n + 1;
        this.n = n;
    }
    private int indexOf(int row , int col){
        checkRowsColumn(row,col);
        return (row - 1) * n + (col -1);
    }

    private void checkRowsColumn(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n)
            throw new IllegalArgumentException();

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        checkRowsColumn(row, col);
        int gridNb = (row - 1) * n + (col -1);
        int upper = (row - 2) * n + (col -1);
        int lower = (row) * n + (col -1);
        int left = (row - 1) * n + (col -2);
        int right = (row - 1) * n + (col);

        if(!isOpen(row,col)){
            grid[gridNb] = true;
            openCellCount++;
            if(row == 1) gridConnection.union(gridNb,top);
            if(row == this.n) gridConnection.union(gridNb,bottom);

            if(row > 1 && isOpen(row - 1 ,col))                                   {
                assert(gridNb > n);
                gridConnection.union(gridNb,upper);
            }
            if(row < this.n && isOpen(row + 1 ,col))                                   {
                assert(gridNb+n < n*n);
                gridConnection.union(gridNb,lower);
            }
            if(col > 1 && isOpen(row,col - 1))                                   {
                gridConnection.union(gridNb,left);
            }
            if(col < this.n && isOpen(row,col + 1))                                   {
                gridConnection.union(gridNb,right);
            }
        }
        // for (int i=1;i<n+1;i++){
        //     for (int j=1;j<n+1;j++){
        //         System.out.print(grid[i*j]+"   ");
        //     }
        //     System.out.println("");
        // }
        // System.out.println("--------------");
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        checkRowsColumn(row, col);
        return grid[(indexOf(row, col))];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        checkRowsColumn(row, col);
        if (!isOpen(row, col)) return false;
         return gridConnection.find(indexOf(row, col)) == gridConnection.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
    return this.openCellCount;
    }

    // does the system percolate?
    public boolean percolates(){
        return gridConnection.find(top) == gridConnection.find(bottom);
    }
}