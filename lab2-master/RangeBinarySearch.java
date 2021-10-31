import java.util.Arrays;
import java.util.Comparator;

public class RangeBinarySearch {

    // Returns the index of the *first* element in `a` that equals the search key,
    // according to the given comparator, or -1 if there is no matching element.
    // Precondition: `a` is sorted according to the given comparator.
    // Complexity: O(log N) comparisons where N is the length of `a`
    public static<T> int firstIndexOf(T[] a, T key, Comparator<T> comparator) {
        int fIndex = 0;
        int lIndex = (a.length - 1);
        int firstIndex = -1;
        boolean found = false;

        while (!found && fIndex <= lIndex ) {
            int mIndex = lIndex + ((fIndex-lIndex) / 2);
            int compare = comparator.compare(a[mIndex], key);

            if (compare < 0) {
                fIndex = mIndex + 1;
            } else if (compare == 0 && mIndex == 0) {
                found = true;
                firstIndex = mIndex;
            } else if (compare == 0 && comparator.compare( a[mIndex - 1], key) != 0) {
                found = true;
                firstIndex = mIndex;
            } else {
                lIndex = mIndex - 1;
            }
        }

        return firstIndex;
    }


    // Returns the index of the *last* element in `a` that equals the search key,
    // according to the given comparator, or -1 if there are is matching element.
    // Precondition: `a` is sorted according to the given comparator.
    // Complexity: O(log N) comparisons where N is the length of `a`
    public static<T> int lastIndexOf(T[] a, T key, Comparator<T> comparator) {
        int fIndex = 0;
        int lIndex = (a.length - 1);
        int lastIndex = -1;
        boolean found = false;

        while (!found && fIndex <= lIndex ) {
            int mIndex = lIndex + ((fIndex-lIndex) / 2);
            int compare = comparator.compare(a[mIndex], key);

            if (compare > 0) {
                lIndex = mIndex - 1;
            } else if (compare == 0 && mIndex == (a.length-1)) {
                found = true;
                lastIndex = lIndex;
            } else if (compare == 0 && comparator.compare(a[mIndex + 1], key) != 0) {
                found = true;
                lastIndex = mIndex;
            } else {
                fIndex = mIndex + 1;
            }
        }

        return lastIndex;
    }
}