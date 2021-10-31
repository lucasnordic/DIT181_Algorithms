import java.util.Random;

/**
 *  For additional documentation on this implementation of quicksort,
 *  see <a href="https://algs4.cs.princeton.edu/23quick">Section 2.3</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Quick {
    
    /**
     * Instantiate the quicksort algorithm with the given options.
     * @param shuffleFirst         If true, shuffle the array before quicksorting it.
     * @param useMedianOfThree     If true, use the median-of-three technique for pivot selection.
     *                             In the recursive call for quicksorting a[lo..hi],
     *                             the first element a[lo] is used as pivot by default.
     *                             Instead, this uses the median of the first, middle, and last element of a[lo..hi].
     * @param insertionSortCutoff  Switch to insertion sort in the recursive call for quicksorting a[lo..hi]
     *                             once the size of a[lo..hi] is less than the given cutoff value.
     */
    public Quick(boolean shuffleFirst, boolean useMedianOfThree, int insertionSortCutoff) {
        this.shuffleFirst = shuffleFirst;
        this.useMedianOfThree = useMedianOfThree;
        this.insertionSortCutoff = insertionSortCutoff;
    }

    private boolean shuffleFirst;
    private boolean useMedianOfThree;
    private int insertionSortCutoff;

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public void sort(int[] a) {
        if (shuffleFirst && a.length > 90/**/) {
            shuffle(a);
//            throw new UnsupportedOperationException("to be implemented");
        }

        sort(a, 0, a.length - 1);
        assert Insertion.isSorted(a);
    }

    // Quicksort the subarray a[lo..hi].
    // This is the recursive workhorse of the algorithm.
    private void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;
        // TODO: check if the size of a[lo..hi] is below the cutoff value / DONE
        if ((hi-lo +1) < insertionSortCutoff && insertionSortCutoff > 0) {
            // TODO: Switch to insertion sort. / DONE
            Insertion.sort(a, lo, hi);
            return;
//            throw new UnsupportedOperationException("to be implemented");
        }

        // Partition the array by moving the two cursors( lo, hi ), towards each other.
        // Every value left of the PIVOT are smaller than the PIVOT. Every value right of... is greater...
        int pivot = partition(a, lo, hi);

        // sort the left array
        sort(a, lo, pivot -1);

        // sort the right array
        sort(a, pivot +1, hi);

        // Check if the array is sorted? assert?
        assert Insertion.isSorted(a, lo, hi);
    }

    // Partition the subarray a[lo..hi] so that
    //   a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private int partition(int[] a, int lo, int hi) {
        int temporary;

        if (useMedianOfThree /**/) {
            // TODO: Find the median of the first, last and middle
            // elements of a[lo..hi], and swap it with a[lo].
            // Hint: Use the static methods medianOfThree and exchange.
            int mid = (lo + hi)/2;
            temporary = a[medianOfThree(a, lo, mid, hi)];
            a[hi] = a[lo];
            a[lo] = temporary;
//            throw new UnsupportedOperationException("to be implemented");
        }

        int i = lo;
        int j = hi + 1;
        
        // a[lo] is used as pivot.
        int pivot = a[lo];


        /**
         * Sorts all elements under "5" to the left
         * Sorts all elements over "5" to the right
         * Does the quicksort
         */

        // a[lo] is unique largest element.
        while (a[++i] < pivot)
            if (i == hi) {
                exchange(a, lo, hi);
                return hi;
            }

        // a[lo] is unique smallest element.
        while (pivot < a[--j])
            if (j == lo + 1)
                return lo;

        // the main loop
        while (i < j) {
            exchange(a, i, j);
            while (a[++i] < pivot);
            while (pivot < a[--j]);
        }

        // Put pivot item v at a[j].
        exchange(a, lo, j);

        // Now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi].
        return j;
    }

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // Exchange a[i] and a[j].
    private static void exchange(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // Return the index of the median element among a[i], a[j], and a[k].
    // For example, if a[j] <= a[k] <= a[i], then return k.
    // if a[4] <= a[5] <= a[10]
    // (The median of some numbers is the middle number if the numbers were sorted.)
    // TODO: implement!
    private static int medianOfThree(int[] a, int lo, int mid, int hi) {
        // Hint: don't try to do anything smart, but just list out all
        // the possible cases. E.g. if a[i] <= a[j] <= a[k], return k.

        if (a[mid] < a[lo]) {
            exchange(a, lo, mid);
        }
        if (a[hi] < a[lo]) {
            exchange(a, lo, hi);
        }
        if (a[mid] < a[hi]) {
            exchange(a, mid, hi);
        }
        return hi;

//        throw new UnsupportedOperationException("to be implemented");
    }

    // Shuffle an array, putting its contents in a random order.
    private static void shuffle(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int j = i + random.nextInt(a.length - i); // uniformly distributed in [i, a.length)
            exchange(a, i, j);
        }
    }

    // Use a fixed random number seed to make testing reproducible.
    private static Random random = new Random(314159265);

    // Just for debugging. Testing. HMMMMMMMMMMMMMMMMMMMMMM
    public static void main(String[] args) {
        int[] arr = {5, 9, 1, 4, 6};

        Quick quick = new Quick(false,true,0);
        quick.sort(arr);


        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

}
