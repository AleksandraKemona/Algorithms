import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private double sd;
    private double mean;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0) throw new IllegalArgumentException();
        results = new double[trials];
        this.trials=trials;
        int testRow;
        int testCol;

        for(int i = 0 ; i < trials;i ++){
            Percolation oneTrial = new Percolation(n);
            while(!oneTrial.percolates()){
                testRow = (StdRandom.uniformInt(n))+1;
                testCol = (StdRandom.uniformInt(n))+1;
                if(!oneTrial.isOpen(testRow,testCol))
                {
                    oneTrial.open(testRow,testCol);
                }
            }
            results[i] = (double)(oneTrial.numberOfOpenSites()) / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean(){
        mean = StdStats.mean(results);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        sd = StdStats.stddev(results);
        return sd;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return (mean - (1.96 * sd)/ Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean + (1.96 * sd)/ Math.sqrt(trials);
    }

//     // test client (see below)
    public static void main(String[] args){
        int number = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats s = new PercolationStats(number,trials);
        System.out.println("Mean = " + s.mean());
        System.out.println("Standard Deviation = "+ s.stddev());
        System.out.println("95% confidence interval = [" +s.confidenceLo() +", " + s.confidenceHi() + "]");
    }

}