package creative.air.java8.common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * A First Taste for JDK8
 * Created by feuyeux@gmail.com
 * Date: Jan 16 2014
 * Time: 2:02 PM
 */
public class FirstApp {

    @FunctionalInterface
    public interface Task {
        void run();

        /*1. given default method in functional interface(or a SAM type)*/
        default void cancel() {
        }
    }

    public static void schedule(Task task) {
        task.run();
    }

    public static void main(String[] args) {
        /*2. Lambda Expressions for anonymous class*/
        {
            /*
            schedule(new Task() {
                @Override
                public void run() {
                    System.out.println("Hello world!");
                }
            });
            */
            schedule(() -> {
                System.out.println("Hello world!");
            });
        }
        List<String> versions = Arrays.asList("JDK7", "JDK8");
         /*3. method reference :: 126 Lambda Expressions & Virtual Extension Methods*/
        {
            /*
            versions.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
            */
            /*
            versions.sort((o1, o2) -> o1.compareTo(o2));
            */
            Comparator<? super String> comparator = String::compareTo;
            versions.sort(comparator);
            System.out.println("JDK versions:" + versions);
        }

        /*4. 107 Bulk Data Operations for Collections*/
        {
            /*
            versions.parallelStream().filter(
              s -> s.equals("JDK8")
            ).map(
              s -> "Develop in " + s + " with Pleasure!"
            ).forEach(
              System.out::println
            );
            */
            Predicate<? super String> predicate = s -> s.equals("JDK8");
            versions.parallelStream().filter(predicate).map(
              s -> String.format("Develop in %s with Pleasure!", s)
            ).forEach(
              System.out::println
            );

            IntStream range = IntStream.range(101, 150);
            range.parallel().filter(i -> i % 10 == 0).sequential().forEach(System.out::println);
        }
    }
}
