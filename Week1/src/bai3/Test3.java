package bai3;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Test3 {
    static HashMap<String, Integer> concurrentHashMap;
    static ExecutorService executorService;
    static int folderSize = 0;
    static List<Future<HashMap<String, Long>>> futures = new ArrayList<>();
    static HashMap<String, Long> allResult = new HashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("START: " + start);
        concurrentHashMap = new HashMap<>();
        executorService = Executors.newFixedThreadPool(5);
        File folder = new File("data");
        File[] files = folder.listFiles();
        if (files != null) {
            folderSize = files.length;
            for (File file : files) {
                ProcessFileThread processFileThread = new ProcessFileThread(folder.getName() + "/" + file.getName());
                Future<HashMap<String, Long>> future = executorService.submit(processFileThread);
                futures.add(future);
            }
        }
        for (Future<HashMap<String, Long>> future : futures) {
            HashMap<String, Long> singleResult = future.get();
            if (singleResult != null) {
                for (String key : singleResult.keySet()) {
                    allResult.put(key, allResult.getOrDefault(key, 0L) + singleResult.get(key));
                }
            }
        }
        checkResult();
        long end = System.currentTimeMillis();
        System.out.println("END " + end);
        System.out.println("TOTAL: " + (end - start)/1000);
    }

    public static void checkResult() {
        executorService.shutdown();
        System.out.println(allResult);
        Set<Map.Entry<String, Long>> entries = allResult.entrySet();
        ArrayList<Map.Entry<String, Long>> entries1 = new ArrayList<>(entries);
        entries1.sort(Comparator.comparingLong(Map.Entry::getValue));
        findTop10Word(entries1);
        findTop10WordLeast(entries1);
    }

    private static void findTop10WordLeast(ArrayList<Map.Entry<String, Long>> entries1) {
        System.out.println("LEAST 10 word");
        List<Map.Entry<String, Long>> result = entries1.subList(0, entries1.size());
        if (entries1.size() >= 10) {
            result = entries1.subList(0, 10);
        }
        System.out.println(result);
    }

    private static void findTop10Word(ArrayList<Map.Entry<String, Long>> entries1) {
        System.out.println("TOP 10 word");
        List<Map.Entry<String, Long>> result = entries1.subList(0, entries1.size());
        if (entries1.size() >= 10) {
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
