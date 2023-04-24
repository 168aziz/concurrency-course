package course.concurrency.forjoinpool;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

public class ForkExampleUnit2 {

//    public static void main(String[] args) {
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        ForkJoinTask<?> task = forkJoinPool.submit(() -> System.out.println(
//                IntStream.range(0, 10000000).average().getAsDouble()
//        ));
//
//
//        task.join();
//    }

    public static void main(String[] args) {
        Locale locale = new Locale("es", "ES");
         Locale.setDefault(Locale.Category.DISPLAY, locale);

        new ForkExampleUnit2().sa(locale);
    }

    public void sa(Locale locale) {
        System.out.println(
                NumberFormat.getCurrencyInstance().format(1.23)
                        + ", " + locale.getDisplayLanguage());
    }
}
