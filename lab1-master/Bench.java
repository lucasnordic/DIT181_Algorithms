import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bench {

    /**
     * Main function
     *
     * Change this method freely.
     * You can choose which sorting algorithms to run and benchmark.
     */
    public static void main(final String[] args) {
        quickSort();
//        quickSortInsertionSortCutoff();

//        insertionSort();
//        mergeSort();
//        industrialStrengthAlgo();
    }

    // QUICKSORT : Testing different QuickSort combinations
    private static void quickSort(){
        System.out.println("=======================================================");
        System.out.println("Info:");
        System.out.println("X Y Z - X is shuffleFirst. Y is useMedianOfThree. Z is use insertionSort");
        System.out.println("F F F - Truth Table\n");

        executionTimeReport("Quick.java: quicksort. F F F - No Improvements", new Quick(false, false, 0)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - InsertionCutoff", new Quick(false, false, 55)::sort);
        executionTimeReport("Quick.java: quicksort. F T F - Median of Three", new Quick(false, true, 0)::sort);
        executionTimeReport("Quick.java: quicksort. F T T - useMedian AND InsertionCutoff", new Quick(false, true, 55)::sort);
        executionTimeReport("Quick.java: quicksort. T F F - Modified Shuffling", new Quick(true, false, 0)::sort);
        executionTimeReport("Quick.java: quicksort. T F T - Modified Shuffling AND InsertionCutoff", new Quick(true, false, 55)::sort);
        executionTimeReport("Quick.java: quicksort. T T F - Shuffling AND useMedian", new Quick(true, true, 0)::sort);
        executionTimeReport("Quick.java: quicksort. T T T - All improvements", new Quick(true, true, 55)::sort);

    }

    // QUICKSORT / INSERTION SORT : To find out insertion sort cutoff
    private static void quickSortInsertionSortCutoff(){
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 10", new Quick(true, false, 10)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 15", new Quick(true, false, 15)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 20", new Quick(true, false, 20)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 25", new Quick(true, false, 25)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 30", new Quick(true, false, 30)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 35", new Quick(true, false, 35)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 40", new Quick(true, false, 40)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 41", new Quick(true, false, 41)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 42", new Quick(true, false, 42)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 43", new Quick(true, false, 43)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 44", new Quick(true, false, 44)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 45", new Quick(true, false, 45)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 50", new Quick(true, false, 50)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 55", new Quick(true, false, 55)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 60", new Quick(true, false, 60)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 65", new Quick(true, false, 65)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 70", new Quick(true, false, 70)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 75", new Quick(true, false, 75)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 80", new Quick(true, false, 80)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 85", new Quick(true, false, 85)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 90", new Quick(true, false, 90)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 95", new Quick(true, false, 95)::sort);
        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 100", new Quick(true, false, 100)::sort);

//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 200", new Quick(true, false, 200)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 400", new Quick(true, false, 400)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 800", new Quick(true, false, 800)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 1600", new Quick(true, false, 1600)::sort);

//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 50", new Quick(true, false, 50)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 50", new Quick(true, false, 50)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 50", new Quick(true, false, 50)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 50", new Quick(true, false, 50)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 50", new Quick(true, false, 50)::sort);
//
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 55", new Quick(true, false, 55)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 55", new Quick(true, false, 55)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 55", new Quick(true, false, 55)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 55", new Quick(true, false, 55)::sort);
//        executionTimeReport("Quick.java: quicksort. F F T - Cutoff: 55", new Quick(true, false, 55)::sort);
    }

    // INSERTION SORT
    private static void insertionSort(){
        executionTimeReport("Insertion.java: insertion sort", Insertion::sort);
    }

    // MERGE SORT
    private static void mergeSort(){
        executionTimeReport("Merge.java: merge sort", Merge::sort);
    }

    // If you want to compare against an industrial-strength algorithm:
    private static void industrialStrengthAlgo(){
        executionTimeReport("Arrays.sort: Java built-in sorting", Arrays::sort);
    }

    // The sample sizes and kinds randomness (0-100) the benchmarking program uses.
    // You can play around with different values!
//    private static int[] SAMPLE_SIZES = new int[] {/*10, 30, 100, 300, 1000, 3000, 10000, 30000, 100000/*, 200000, 500000*/};
    private static int[] SAMPLE_SIZES = new int[] {10, 30, 100, 300, 1000, 3000, 10000, 30000, 100000/*, 200000/*, 500000/*, 1000000, 2000000, 4000000/**/};
//    private static int[] SAMPLE_SIZES = new int[] {10000, 100000};

    //    private static int[] SAMPLE_SIZES = new int[] {5,10,15,20,25,30,35,40,45,50,55,60};
    //    private static int[] SAMPLE_SIZES = new int[] {30};

    // Insertion sort;
    //    private static int[] SAMPLE_SIZES = new int[] {150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 170};
    //    private static int[] SAMPLE_SIZES = new int[] {10, 30, 100, 150, 160, 170, 180, 190, 200, 300, 1000, 3000, 10000, 30000, 100000};

    // Insane tests
    //    private static int[] SAMPLE_SIZES = new int[] {10, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45, 48, 51, 54, 57, 60, 63, 66, 69, 72, 75, 78, 81, 84, 87, 90, 93, 96, 99, 100, 300, 1000, 3000, 10000, 30000, 100000};
    //    private static int[] SAMPLE_SIZES = new int[] {/*10, 20, 40, 80, 160, 320, 640, 1280, 2560,*/ 5120, 10240, 20480, 40960, 81920/*, 163840, 327680/*, 655360, 1310720/**/};
    private static final int[] RANDOMNESS = new int[] {100, 5, 0};

    //
    // HERE BE DRAGONS!
    //
    // You don't have to look at the rest of this file.
    // It's just the testing and benchmarking program.
    //

    /** Test data generator **/

    // Generates a random array of size 'size'.
    // Part of the array is sorted, while the rest is chosen uniformly
    // at random; the 'randomness' parameter sets what percent of the
    // array is chosen at random.
    public static int[] generateSample(int size, int randomness) {
        int[] sample = new int[size];

        Random random = new Random(12345678 * size);
        int previousElement = 0;
        for (int i = 0; i < size; i++) {
            if (random.nextInt(100) >= randomness) {
                int randomOffset = random.nextInt(3);
                int currentElement = previousElement + randomOffset;
                sample[i] = currentElement;
                previousElement = currentElement;
            } else {
                sample[i] = random.nextInt(size);
            }
        }

        return sample;
    }

    public static String getRandomnessDescription(int randomness) {
        switch (randomness) {
            case   0: return "Sorted";
            case 100: return "Random";
            default:  return (100 - randomness) + "% sorted";
        }
    }

    /** Code to test the correctness of a sorting algorithm **/

    @SuppressWarnings("serial")
    private static class TestException extends Exception {
    }

    private static void testAlgorithm(Consumer<int[]> algorithm) throws TestException {
        for (int size = 0; size <= 1000; ++size)
            for (int randomness : new int[] {100, 5, 0})
                check(generateSample(size, randomness), algorithm);
    }

    private static void check(final int[] array, Consumer<int[]> algorithm) throws TestException {
        final int[] reference = array.clone();
        Arrays.sort(reference);

        // We don't catch exceptions so as not to disturb debuggers.
        int[] result = array.clone();
        withExceptionHandler(() -> algorithm.accept(result), e -> {
            if (!(e instanceof UnsupportedOperationException))
                failed(array, reference);

            System.out.println("Threw exception:");
            e.printStackTrace(System.out);
        });

        if (!Arrays.equals(result, reference)) {
            failed(array, reference);
            System.out.println("Actual answer: " + show(result));
            throw new TestException();           
        }
    }

    private static void withExceptionHandler(Runnable f, Consumer<Throwable> handler) {
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable e) {
                handler.accept(e);
            }
        });
        f.run();
        Thread.currentThread().setUncaughtExceptionHandler(null);
    }

    private static void failed(int[] array, int[] reference) {
        System.out.println("Test failed! There is a bug in the sorting algorithm.");
        System.out.println("Input array: " + show(array));
        System.out.println("Expected answer: " + show(reference));
    }

    private static String show(int[] array) {
        return Arrays.stream(array).mapToObj(Integer::toString).collect(Collectors.joining(", ", "{", "}"));
    }

    /** Code to measure the performance of a sorting algorithm **/

    // Execute an algorithm on an input and return its runtime.
    private static String execute(Consumer<int[]> algorithm, int[] input) {
        // To get accurate results even for small inputs, we repeat
        // the algorithm several times in a row and count the total time.
        // We pick the number of repetitions automatically so that
        // the total time is at least 10ms.
        //
        // To pick the number of repetitions, we start by assuming
        // that one repetition will be enough. We then execute the
        // algorithm and measure how long it takes. If it took less
        // than 10ms, we scale up the number of repetitions by
        // an appropriate factor. E.g., if the algorithm only took
        // 1ms, we will multiply the number of repetitions by 10.
        // We then repeat this whole process with the new number of
        // repetitions.
        //
        // Once the repetitions take more than 10ms, we try it three
        // times and take the smallest measured runtime. This avoids
        // freakish results due to e.g. the garbage collector kicking
        // in at the wrong time.

        // Minimum acceptable value for total time.
        final long target = 10000000;
        // How many times to re-measure the algorithm once it hits the
        // target time.
        final int MAX_LIVES = 3;
        // How many repetitions we guess will be enough.
        int repetitions = 1;
        // The lowest runtime we saw with the current number of repetitions.
        long runtime = Long.MAX_VALUE;
        // How many times we've measured after hitting the target time.
        int lives = MAX_LIVES;
        while(true) {
            // Build the input arrays in advance to avoid memory
            // allocation during testing.
            int[][] inputs = new int[repetitions][];
            for (int i = 0; i < repetitions; i++)
                inputs[i] = Arrays.copyOf(input, input.length);
            // Try to reduce unpredictability
            System.gc();
            Thread.yield();

            // Run the algorithm
            long startTime = System.nanoTime();
            for (int i = 0; i < repetitions; i++)
                algorithm.accept(inputs[i]);
            long endTime = System.nanoTime();
            runtime = Math.min(runtime, endTime - startTime);

            // If the algorithm is really slow, we don't
            // need to measure too carefully
            if (repetitions == 1 && runtime >= 30*target)
                break;
            if (runtime >= target) {
                // Ran for long enough - reduce number of lives by one.
                if (lives == 0) break; else lives--;
            } else {
                // Didn't run for long enough.
                // Increase number of repetitions to try to hit
                // target - but at least double it, and at most
                // times by 5.
                if (runtime == 0)
                    repetitions *= 5;
                else {
                    double factor = target / runtime;
                    if (factor < 2) factor = 2;
                    if (factor > 5) factor = 5;
                    repetitions *= factor;
                }
                runtime = Long.MAX_VALUE;
                lives = MAX_LIVES;
            }
        }
        return String.format("%6f", (double)runtime / ((long)repetitions * 1000000));
    }

    private static void runWithLargeStack(int stackSize, Runnable f) {
        Thread t = new Thread(null, f, "large stack thread", stackSize);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void executionTimeReport(String name, Consumer<int[]> algorithm) {
        final int sizeLength = 7;
        final int timeLength = 13;
        
        Function<Integer, Function<Object, String>> pad =
            n -> x -> String.format("%" + n + "s", x);
        BiConsumer<String, Stream<String>> printLine = (delim, s) -> System.out.println(s.collect(Collectors.joining(delim)));
        Runnable printSep = () -> printLine.accept("===",
            Stream.concat(Stream.of(sizeLength), Collections.nCopies(RANDOMNESS.length, timeLength).stream())
                .map(n -> String.join("", Collections.nCopies(n, "="))));
        BiConsumer<Object, Stream<Object>> printRow = (size, stream) -> printLine.accept(" | ",
            Stream.concat(Arrays.asList(size).stream().map(pad.apply(sizeLength)), stream.map(pad.apply(timeLength))));

        printSep.run();
        System.out.printf("  %s (times in ms)%n", name);
        printSep.run();
        
        runWithLargeStack(32 * 1024 * 1024, () -> {
            try {
                testAlgorithm(algorithm);
                printRow.accept("Size", Arrays.stream(RANDOMNESS).mapToObj(r -> getRandomnessDescription(r)));

                for (int size : SAMPLE_SIZES)
                    printRow.accept(size, Arrays.stream(RANDOMNESS).mapToObj(r -> execute(algorithm, generateSample(size, r))));
            } catch (TestException e) {
            }
        });

        System.out.println();
    }
}
