import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double ZCRIT = 1.96; // for 95% confidence interval
    private int runs; // count of runs
    private double[] thresh; // array to store threshold values for each trial
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {   
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        runs = T;
        thresh = new double[runs];

        for (int i = 0; i < runs; i++) {
            Percolation trial = new Percolation(N);
            
            // while-loop to open new points until percolation occurs
            while (!trial.percolates()) { 
                int x = StdRandom.uniform(0, N);
                int y = StdRandom.uniform(0, N);
                if (!trial.isOpen(x, y)) trial.open(x, y);
            }
            thresh[i] = (double) trial.numberOfOpenSites() / (N*N);
        }
    }
    // sample mean of percolation threshold
    public double mean() {                  
        return StdStats.mean(thresh);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {                
        return StdStats.stddev(thresh);
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLow() {         
        return mean() - ZCRIT*stddev() / Math.sqrt(runs);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {        
        return mean() + ZCRIT*stddev() / Math.sqrt(runs);
    }
}