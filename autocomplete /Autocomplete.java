import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Autocomplete 
{
    private final Term[] term;
    private Term[] sorted;
    
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms)
    {
        if (terms == null)
            throw new java.lang.NullPointerException();
        
        for (int i = 0; i < terms.length; i++)
        {
            if (terms[i] == null) 
                throw new java.lang.NullPointerException();
        }
        term = terms;
        sorted = terms;
        
        // MergeX.sort(sorted);
    }
    
    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix)
    {
        if (prefix == null)
            throw new java.lang.NullPointerException();
        
        MergeX.sort(sorted, Term.byPrefixOrder(prefix.length()));
        
        Term pref = new Term(prefix, 1);
        int first = BinarySearchDeluxe.firstIndexOf(sorted, pref, Term.byPrefixOrder(prefix.length()));
        int last = BinarySearchDeluxe.lastIndexOf(sorted, pref, Term.byPrefixOrder(prefix.length()));
        
        Term[] match = new Term[numberOfMatches(prefix)];
        
        for (int i = first; i <= last; i++)
        {
            match[i - first] = term[i];
        }
        
        MergeX.sort(match, Term.byReverseWeightOrder());

        return match;
    }
    
    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix)
    {
        if (prefix == null)
            throw new java.lang.NullPointerException();
        MergeX.sort(sorted, Term.byPrefixOrder(prefix.length()));
        Term pref = new Term(prefix, 1);
        int first = BinarySearchDeluxe.firstIndexOf(sorted, pref, Term.byPrefixOrder(prefix.length()));
        int last = BinarySearchDeluxe.lastIndexOf(sorted, pref, Term.byPrefixOrder(prefix.length()));
        
        return (last - first + 1);
    }
    
    public static void main(String[] args) 
    {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) 
        {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        
        // read in queries from standard input and print out the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
        
    }
    
}
