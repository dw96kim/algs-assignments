import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Board {
    
    private int[][] board;
    private int n; // dimension
    
    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles)
    {
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }
    
    private int xyIndex(int i, int j) // returns 1D representation
    {         
        if (i == n - 1 && j == n - 1) return 0;
        else return n*i + j + 1;
    }
    
    private int indX(int c) // returns col index
    {
        if (c == 0) return n - 1;
        else 
        {
            int x = (c - 1) % n;
            return x;
        }
    }
    
    private int indY(int c) // returns row index
    {
        if (c == 0) return n - 1;
        else 
        {
            int y = (c - 1) / n;
            return y;
        }
    }
    
    private static void exch(int[][] a, int i1, int j1, int i2, int j2)
    {
        int swap = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = swap;
    }
    
    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j)
    {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1)
            throw new IndexOutOfBoundsException();
        return board[i][j];
    }
    
    // board size N
    public int size()           
    { 
        return n;
    }
    
    
    // number of tiles out of place
    public int hamming()
    {
        int h = 0;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                int value = tileAt(i, j);
                if (value != 0 && value != xyIndex(i, j)) h++;
            }
        }
        return h;
    }
    
    // sum of Manhattan distances between tiles and goal
    public int manhattan()                 
    {
        int x = 0;
        int y = 0;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                int value = tileAt(i, j);
                if (value != 0 && value != xyIndex(i, j))
                {
                    y += Math.abs(indY(value) - i);
                    x += Math.abs(indX(value) - j);
                }
            }
        }
        return x + y;
    }
    
    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }
    
    // is this board solvable?
    public boolean isSolvable()
    {
        int inversion = 0;
        int blankRow = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = i; k < n; k++) {
                    for (int m = 0; m < n; m++)
                    {
                        if (tileAt(i, j) == 0)
                            blankRow = i;
                        if (tileAt(i, j) != 0 && tileAt(k, m) != 0)
                        {
                            if (tileAt(i, j) > tileAt(k, m))
                            {
                                if (k == i && m < j) inversion += 0;
                                else inversion++;
                            }
                        }
                    }
                }
            }
        }
        if (n % 2 == 1) return inversion % 2 == 0;
        else return (inversion + blankRow) % 2 == 1;
    }
    
    
    // does this board equal y?
    public boolean equals(Object y)        
    {   
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.size() != that.size()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((this.tileAt(i, j) != that.tileAt(i, j))) return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() 
    {
        Stack<Board> stackOfBoards = new Stack<Board>();
        
        int row = 0;
        int col = 0;
        
        // find index of tile 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tileAt(i, j) == 0) 
                {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        
        int[][] stack = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stack[i][j] = board[i][j];
            }
        }
        
        if (row != 0)
        {
            exch(stack, row, col, row - 1, col);
            Board stacked = new Board(stack);
            stackOfBoards.push(stacked);
            exch(stack, row, col, row - 1, col);
        }
        
        if (row != n - 1)
        {
            exch(stack, row, col, row + 1, col);
            Board stacked = new Board(stack);
            stackOfBoards.push(stacked);
            exch(stack, row, col, row + 1, col);
        }
        
        if (col != 0)
        {
            exch(stack, row, col, row, col - 1);
            Board stacked = new Board(stack);
            stackOfBoards.push(stacked);
            exch(stack, row, col, row, col - 1);
        }
        
        if (col != n - 1)
        {
            exch(stack, row, col, row, col + 1);
            Board stacked = new Board(stack);
            stackOfBoards.push(stacked);
            exch(stack, row, col, row, col + 1);
        }
        
        return stackOfBoards;
    }
    
    
// string representation of this board (in the output format specified below)
    // from checklist
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) 
    {
        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        
        // test methods
        StdOut.println("hamming = " + initial.hamming());
        StdOut.println("manhattan = " + initial.manhattan());
        StdOut.println("neighbors = " + initial.neighbors());
        StdOut.println("isSolvable = " + initial.isSolvable());
        StdOut.println("isGoal = " + initial.isGoal());
        
    }
}
