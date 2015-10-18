import java.util.Comparator;

public class BinarySearchDeluxe 
{    
    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator)
    {
        if (a == null || key == null || comparator == null)
            throw new java.lang.NullPointerException();
        
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi)
        {
            int mid = lo + (hi - lo) / 2;
            if (comparator.compare(key, a[mid]) < 0) 
                hi = mid - 1;
            if (comparator.compare(key, a[mid]) > 0)
                lo = mid + 1;
            else
            {
                for (int i = lo; i <= mid; i++)
                {
                    if (a[i] == a[mid]) return i;
                }
            }
        }
        return -1; 
    }
    
    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator)
    {
        if (a == null || key == null || comparator == null)
            throw new java.lang.NullPointerException();
        
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi)
        {
            int mid = lo + (hi - lo) / 2;
            if (comparator.compare(key, a[mid]) < 0) 
                hi = mid - 1;
            if (comparator.compare(key, a[mid]) > 0)
                lo = mid + 1;
            else
            {
                for (int i = hi; mid <= i ; i--)
                {
                    if (a[i] == a[mid]) return i;
                }
            }
        }
        return -1; 
    }
}

