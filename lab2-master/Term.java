
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class Term {
    private String word;
    private long weight;

    // Initializes a term with a given word and weight.
    public Term(String word, long weight) {
        this.word = word;
        this.weight = weight;
    }

    // Gets the word.
    public String getWord() {
        return word;
    }

    // Gets the weight.
    public long getWeight() {
        return weight;
    }

    // Extracts a prefix from the word.
    // If `len` is larger than the word length, the prefix is the entire word.
    public String getPrefix(int len) {
        int termLength = this.word.length();
        if (len < termLength) {
            return this.word.substring(0,len);
        }
        return this.word;
    }

    // Compares two terms in case-insensitive lexicographic order.
    public static final Comparator<Term> byLexicographicOrder = new Comparator<Term>() {
        @Override
        public int compare(Term o1, Term o2) {
            return o1.getWord().compareToIgnoreCase(o2.getWord());
        }
    };

    // Compares two terms in descending order by weight.
    public static final Comparator<Term> byReverseWeightOrder = new Comparator<Term>() {
        @Override
        public int compare(Term o1, Term o2) {
            return Long.compare(o2.getWeight(), o1.getWeight());
        }
    };

    // This method returns a comparator that compares the two terms in case-insensitive
    // lexicographic order, but using only the first k characters of each word.
    public static Comparator<Term> byPrefixOrder(int k) {
        return new Comparator<Term>() {
            @Override
            public int compare(Term term1, Term term2) {
                return term1.getPrefix(k).compareToIgnoreCase(term2.getPrefix(k));
            }
        };
    }

    /*
    // If you are stuck with creating comparators, here is a silly example that considers all integers equal:
    public static final Comparator<Integer> example0 = new Comparator<Integer>() {
        public int compare(Integer a, Integer b) {
            return 0;
        }
    };

    // And here is the same example using functional syntax:
    public static final Comparator<Integer> example1 = (a, b) -> {
        return 0;
    };

    // This is the same as the following:
    public static final Comparator<Integer> example2 = (a, b) -> 0;
    */

    // Returns a string representation of this term in the following format:
    // the weight, followed by whitespace, followed by the word.
    public String toString() {
        return String.format("%12d    %s", this.getWeight(), this.getWord());
    }
}
