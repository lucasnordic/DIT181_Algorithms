import java.util.*;

public class Autocomplete {
    private Term[] dictionary;

    // Initializes the dictionary from the given array of terms.
    public Autocomplete(Term[] dictionary) {
        this.dictionary = dictionary;
        sortDictionary();
    }

    // Sorts the dictionary in *case-insensitive* lexicographic order.
    // Complexity: O(N log N) where N is the number of dictionary terms
    private void sortDictionary() {
        Arrays.sort(dictionary, Term.byLexicographicOrder);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    // Precondition: the internal dictionary is in lexicographic order.
    // Complexity: O(log N + M log M) where M is the number of matching terms
    public Term[] allMatches(String prefix) {
        Term[] matchingTerms = new Term[]{};   // for storing found matching terms.
        Term term = new Term(prefix,0); // Initializing Term from prefix. Later used for comparisons

        int first = findFirstIndexOf(term);
        if (first != -1) { // No reason to find the last index if we couldn't find a match during the first check.
            int last = findLastIndexOf(term);

            // Here we copy every element between first and last into a new array. Or if we get "-1" from either first
            matchingTerms = Arrays.copyOfRange(dictionary,first,(last+1));// I have to add +1 here but I can't figure out why...
            Arrays.sort(matchingTerms, Term.byReverseWeightOrder);
        }

        return matchingTerms;
    }

    // Returns the number of terms that start with the given prefix.
    // Precondition: the internal dictionary is in lexicographic order.
    // Complexity: O(log N) where N is the number of dictionary terms
    public int numberOfMatches(String prefix) {
        Term term = new Term(prefix,1); // Initializing Term from prefix. Later used for comparisons

        int first = findFirstIndexOf(term);
        if (first == -1) // No reason to find the last index if we couldn't find a match during the first check.
            return 0;
        int last = findLastIndexOf(term);


        return (last+1) - first;
    }

    private int findFirstIndexOf(Term term){
        return RangeBinarySearch.firstIndexOf(this.dictionary, term, Term.byPrefixOrder(term.getWord().length()));
    }

    private int findLastIndexOf(Term term){
        return RangeBinarySearch.lastIndexOf(this.dictionary, term, Term.byPrefixOrder(term.getWord().length()));
    }
}
