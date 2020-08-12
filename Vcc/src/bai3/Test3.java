package bai3;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static bai3.GenData.totalString;

public class Test3 {
    //    static ArrayBlockingQueue<Runnable> queues = new ArrayBlockingQueue<>(100);
//    static int corePoolSize = 5;
//    static int maximumPoolSize = 10;
//    static int keepAliveTime = 500;
//    static TimeUnit timeUnit = TimeUnit.SECONDS;
    static HashMap<String, Integer> concurrentHashMap;
    static ExecutorService executorService;
    static int folderSize = 0;
    static List<Future<Void>> futures = new ArrayList<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
//        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, queues, rejectedExecutionHandler);
        GenData.genData();
        concurrentHashMap = new HashMap<>();
        executorService = Executors.newFixedThreadPool(5);
        File folder = new File("data");
        File[] files = folder.listFiles();
        if (files != null) {
            folderSize = files.length;
            for (File file : files) {
                ProcessFileThread processFileThread = new ProcessFileThread(folder.getName() + "/" + file.getName());
                Future<Void> future = executorService.submit(processFileThread);
                futures.add(future);
            }
        }
        for (Future<Void> future : futures) {
            future.get();
        }
        checkResult();
    }

    public static void checkResult() {
        executorService.shutdown();
        System.out.println(concurrentHashMap);
        Set<Map.Entry<String, Integer>> entries = concurrentHashMap.entrySet();
        ArrayList<Map.Entry<String, Integer>> entries1 = new ArrayList<>(entries);
        entries1.sort(Comparator.comparingInt(Map.Entry::getValue));
        findTop10Word(entries1);
        findTop10WordLeast(entries1);
    }

    private static void findTop10WordLeast(ArrayList<Map.Entry<String, Integer>> entries1) {
        System.out.println("LEAST 10 word");
        List<Map.Entry<String, Integer>> result = entries1.subList(0, entries1.size());
        if (entries1.size() >= 10) {
            result = entries1.subList(0, 10);
        }
        System.out.println(result);
    }

    private static void findTop10Word(ArrayList<Map.Entry<String, Integer>> entries1) {
        System.out.println("TOP 10 word");
        List<Map.Entry<String, Integer>> result = entries1.subList(0, entries1.size());
        if(entries1.size()>=10){
            result = entries1.subList(entries1.size() - 10, entries1.size());
        }
        Collections.reverse(result);
        System.out.println(result);
        Collections.reverse(result);
    }

    public static synchronized void addToMap(String key) {
        concurrentHashMap.put(key, concurrentHashMap.getOrDefault(key, 0) + 1);
    }
}
