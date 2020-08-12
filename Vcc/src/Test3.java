import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.*;

public class Test3 {
    //    static ArrayBlockingQueue<Runnable> queues = new ArrayBlockingQueue<>(100);
//    static int corePoolSize = 5;
//    static int maximumPoolSize = 10;
//    static int keepAliveTime = 500;
//    static TimeUnit timeUnit = TimeUnit.SECONDS;
    static ConcurrentHashMap<String, Integer> concurrentHashMap;
    static ExecutorService executorService;
    static int folderSize = 0;
    static HashMap<String, Integer> allResult;
    static List<Future<HashMap<String, Integer>>> futures = new ArrayList<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, queues, rejectedExecutionHandler);

        concurrentHashMap = new ConcurrentHashMap<>();
        executorService = Executors.newFixedThreadPool(5);
        File folder = new File("data");
        File[] files = folder.listFiles();
        if (files != null) {
            folderSize = files.length;
            for (File file : files) {
                ProcessFileThread processFileThread = new ProcessFileThread(folder.getName() + "/" + file.getName());
                Future<HashMap<String, Integer>> future = executorService.submit(processFileThread);
                futures.add(future);
            }
        }
        allResult = new HashMap<>();
        for (Future<HashMap<String, Integer>> future : futures) {
            HashMap<String, Integer> map = future.get();
            map.forEach((s, value) -> allResult.put(s, allResult.getOrDefault(s, 0) + value));
        }
        checkResult();
    }

    public static void checkResult() {
        allResult.forEach((s, integer) -> System.out.println(s + " " + integer));
        executorService.shutdown();
        findTop10Word();
        findTop10WordLeast();
    }

    private static void findTop10WordLeast() {
        TreeMap<String, Integer> treeMap = new TreeMap<>(allResult);
        System.out.println(treeMap);
    }

    private static void findTop10Word() {

    }

}
