import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF qufind; // datastructure for union-find
    private WeightedQuickUnionUF backwash; // 2nd union find to avoid backwash
    private boolean[][] perc; // 2D array to track open sites
    private int dim; // size of the array 
    private int top; // index for virtual site connected to all first row
    private int bot; // index for virtual site connected to all bottom row
    private int opencount = 0;
    
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {              
        if (N <= 0) throw new IllegalArgumentException();
        dim = N;
        qufind = new WeightedQuickUnionUF(dim*dim+2);
        backwash = new WeightedQuickUnionUF(dim*dim+1);
        top = 0;
        bot = dim*dim + 1;
        perc = new boolean[dim][dim];
    }    
    // helper method to convert coordinates to indices
    private int xyIndex(int x, int y) {         
        return dim*y + x + 1;
    }
    // helper method to validate coordinate
    private void validate(int x, int y) { 
        if (0 <= x && x < dim && 0 <= y && y < dim) return;
        else throw new IndexOutOfBoundsException();
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {      
        validate(row, col); // check to see if coordinates are within bounds
        if (!perc[row][col]) {
            perc[row][col] = true;
            opencount++;
        }
        int index = xyIndex(row, col);
        if (row == 0) {
            qufind.union(index, top);
            backwash.union(index, top);
        }
        if (row == dim - 1)
            qufind.union(index, bot);
        // connecting now open site to adjacent sites
        if (0 < row && perc[row - 1][col]) {
            qufind.union(index, xyIndex(row - 1, col));
            backwash.union(index, xyIndex(row - 1, col));
        }
        if (row < dim - 1 && perc[row + 1][col]) {
            qufind.union(index, xyIndex(row + 1, col));
            backwash.union(index, xyIndex(row + 1, col));
        }
        if (0 < col && perc[row][col - 1]) {
            qufind.union(index, xyIndex(row, col - 1));
            backwash.union(index, xyIndex(row, col - 1));
        }
        if (col < dim - 1 && perc[row][col + 1]) {
            qufind.union(index, xyIndex(row, col + 1));
            backwash.union(index, xyIndex(row, col + 1));
        }
    }
    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        validate(row, col); // check to see if coordinates are within bounds
        return perc[row][col];
    }
    public boolean isFull(int row, int col) { // is the site (row, col) full?
        validate(row, col); // check to see if coordinates are within bounds
        return backwash.connected(top, xyIndex(row, col));        
    }
    public int numberOfOpenSites() {         // number of open sites
        return opencount;
    }
    public boolean percolates() {            // does the system percolate? 
        return qufind.connected(top, bot);
    }
}