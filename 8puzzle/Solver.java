import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Solver 
{   
    private Board initialBoard; // first board from data
    private int minMoves; // minimum number of moves
    private SearchNode end; // last board
    // private Queue<Board> solveQueue = new Queue<Board>();
    
    private class SearchNode implements Comparable<SearchNode>
    {
        private Board board; // board
        private int moves; // moves already made
        private SearchNode prev; // previous node 
        private int priority; // priority
        
        public SearchNode(Board puzzle, int n, SearchNode prevNode) 
        {
            board = puzzle;
            moves = n;
            prev = prevNode;
            priority = puzzle.manhattan() + moves;
        }
        
        // compare using Manhattan priority function
        public int compareTo(SearchNode that)
        {
            if (this.priority < that.priority) return -1;
            if (this.priority > that.priority) return +1;
            return 0;
        }
    } 
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        if (!initial.isSolvable()) throw new IllegalArgumentException();
        if (initial == null) throw new NullPointerException();
        
        SearchNode search = new SearchNode(initial, 0, null);
        initialBoard = initial;
        
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(search);
        
        // repeat until dequeued node corresponds to goal board
        while (!pq.min().board.isGoal()) 
        {
            SearchNode before = pq.delMin();
            Iterable<Board> neighbors = before.board.neighbors();
            
            for (Board neighBoard : neighbors)
            {
                SearchNode next = 
                    new SearchNode(neighBoard, before.moves + 1, before);
                if (next.moves >= 2) 
                {
                    if (!next.board.equals(next.prev.prev.board))
                        pq.insert(next);
                }
                else pq.insert(next);
            }
        }
        end = pq.delMin();
        minMoves = end.moves;
    }
    
    
    // min number of moves to solve initial board    
    public int moves()
    {
        return minMoves;
    }
    
    // sequence of boards in a shortest solution    
    public Iterable<Board> solution()
    {
        Stack<Board> solveQueue = new Stack<Board>();
        
        SearchNode ref = end;
        while (ref != null)
        {
            solveQueue.push(ref.board);
            ref = ref.prev;
        }
        return solveQueue;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        
        // check if puzzle is solvable; if so, solve it and output solution
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
        // if not, report unsolvable
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}