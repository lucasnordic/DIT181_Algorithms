import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

// The main plagiarism detection program.
// You only need to change buildIndex() and findSimilarity().
public class Lab3 {

    public static void main(String[] args) throws IOException {
        // Read document paths from provided folder name.
        if (args.length < 1) {
            System.err.println("Usage: you have to provide a program argument:");
            System.err.println("  (1) the name of the directory to scan");
            System.exit(1);
        }
        Path[] paths = Files.list(Paths.get(args[0])).toArray(Path[]::new);
        Arrays.sort(paths);

        // Stopwatches time the execution of each phase of the program.
        Stopwatch stopwatch = new Stopwatch();
        Stopwatch stopwatch2 = new Stopwatch();

        System.out.println("Time to run:");

        // Read all input files.
        ScapegoatTree<Path, Ngram[]> files = readPaths(paths);
        stopwatch.finished("   Reading all input files");

        // Build index of n-grams (not implemented yet).
        ScapegoatTree<Ngram, ArrayList<Path>> index = buildIndex(files);
        stopwatch.finished("   Building n-gram index");

        // Compute similarity of all file pairs.
        ScapegoatTree<PathPair, Integer> similarity = findSimilarity(files, index);
        stopwatch.finished("   Computing similarity scores");

        // Find most similar file pairs, arranged in decreasing order of similarity.
        ArrayList<PathPair> mostSimilar = findMostSimilar(similarity);
        stopwatch.finished("   Finding the most similar files");
        stopwatch2.finished("   In total the program");
        System.out.println();

        // Print out some statistics.
        System.out.println("Balance statistics:");
        System.out.printf("%-16s| %s %s", "   files", files.statistics(), "\n");
        System.out.printf("%-16s| %s %s", "   index", index.statistics(), "\n");
        System.out.printf("%-16s| %s %s", "   similarity", similarity.statistics(), "\n");
        System.out.println();

        // Print out the plagiarism report!
        System.out.println("Plagiarism report:");
        mostSimilar.stream().limit(50).forEach((PathPair pair) -> {
            System.out.printf("%5d similarity: %s%n", similarity.get(pair), pair);
        });
    }

    // Phase 1: Read in each file and chop it into n-grams.
    static ScapegoatTree<Path, Ngram[]> readPaths(Path[] paths) throws IOException {
        ScapegoatTree<Path, Ngram[]> files = new ScapegoatTree<Path, Ngram[]>();
        for (Path path : paths) {
            String contents = new String(Files.readAllBytes(path));
            Ngram[] ngrams = Ngram.ngrams(contents, 5);
            // Remove duplicates from the ngrams list
            // Uses the Java 8 streams API - very handy Java feature
            // which we don't cover in the course. If you want to
            // learn about it, see e.g.
            // https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#package.description
            // or https://stackify.com/streams-guide-java-8/
            ngrams = Arrays.stream(ngrams).distinct().toArray(Ngram[]::new);
            files.put(path, ngrams);
        }

        return files;
    }

    /**
     * Here we create an index of all the Ngrams
     */
    // Phase 2: Build index of n-grams (definitely implemented by now).
    static ScapegoatTree<Ngram, ArrayList<Path>> buildIndex(ScapegoatTree<Path, Ngram[]> files) {
        ScapegoatTree<Ngram, ArrayList<Path>> index = new ScapegoatTree<Ngram, ArrayList<Path>>();
        for (Path path : files.keys()) {
            for (Ngram ngram : files.get(path)) {
                if (index.contains(ngram)) {
                    index.get(ngram).add(path);
                } else {
                    ArrayList<Path> nPaths = new ArrayList<>();
                    nPaths.add(path);
                    index.put(ngram, nPaths);
                }
            }
        }

        return index;
    }

    static ScapegoatTree<PathPair, Integer> findSimilarity (
            ScapegoatTree <Path, Ngram[]> files,
            ScapegoatTree <Ngram, ArrayList < Path >> index)
    {
        ScapegoatTree<PathPair, Integer> similarity = new ScapegoatTree<PathPair, Integer>();
        for (Path path : files.keys()) {
            for (Ngram ngram : files.get(path)) {
                for (Path paths : index.get(ngram)) {
                    if (!path.equals(paths)) {
                        PathPair pair = new PathPair(path, paths);
                        if (similarity.contains(pair)) {
                            similarity.put(pair, similarity.get(pair) + 1);
                        } else {
                            similarity.put(pair, 1);
                        }
                    }
                }
            }
        }

        return similarity;
    }

    // Phase 4: find all pairs of files with more than 30 n-grams
    // in common, sorted in descending order of similarity.
    static ArrayList<PathPair> findMostSimilar (ScapegoatTree < PathPair, Integer > similarity){
        // We use the Java 8 streams API - see the comment to the
        // 'readPaths' method for more information.
        return
                // Convert allPathPairs into a stream. This is a bit more
                // complicated than it should be, because BST doesn't
                // implement the streaming API. If BST came from the Java
                // standard library, we could just write
                // 'allPathPairs.stream()' or 'similarity.keys().stream()'.
                StreamSupport.stream(similarity.keys().spliterator(), false)
                        // Keep only dictinct pairs with more than 30 n-grams in common.
                        .filter(pair -> !pair.path1.equals(pair.path2) && similarity.get(pair) >= 30)
                        // Remove duplicates - pairs (path1, path2) and (path2, path1).
                        .map(PathPair::canonicalise)
                        .distinct()
                        // Sort to have the most similar pairs first.
                        .sorted(Comparator.comparing(similarity::get).reversed())
                        // Store the result in an ArrayList.
                        .collect(Collectors.toCollection(ArrayList<PathPair>::new));
    }

}
