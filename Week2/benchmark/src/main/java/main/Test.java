package main;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {
    static int connections = 40000;
    static int threadSize = 4;
    static String url = "http://localhost:8080/prime";
    static ExecutorService executorService;
    static List<Callable<Report>> callables;
    static List<Report> reports = new ArrayList<>();
    static List<Double> timePerRequests = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(threadSize);
        callables = new ArrayList<>();
        if (!connectSuccess()) {
            System.out.println("unable to connect to: " + url);
        }
        printSystemInfo();
        prepareThreadTest();
        System.out.println("Running test " + url);
        System.out.println(threadSize + " threads and " + connections + " connections");
        runTest();
        printResult();
    }

    private static void printResult() {
        int complete = 0, fail = 0;
        double stTotal = 0.0, min = Double.MAX_VALUE, max = 0.0;
        for (Report report : reports) {
            complete += report.getReqSuccess();
            fail += report.getReqFail();
            stTotal += report.getSt();
            if (report.getMin() < min) min = report.getMin();
            if (report.getMax() > max) max = report.getMax();
            timePerRequests.addAll(report.getTimeRequests());
        }
        double mean = stTotal / threadSize;
        double sum = 0.0;
        for (Report report : reports) {
            sum += Math.pow((report.getSt() - mean), 2);
        }
        double stdev = Math.sqrt(sum / threadSize);
        printValue("complete", complete);
        printValue("fail", fail);
        printValue("mean", mean);
        printValue("+/- stdev", stdev);
        printValue("min", min);
        printValue("max", max);
        printValue("P95", calculatorP95());
        printValue("P99", calculatorP99());
    }

    private static void printValue(String name, int value) {
        System.out.println(name + ": " + value);
    }

    private static void printValue(String name, double value) {
        System.out.println(name + ": " + df.format(value / 1000000) + "ms");
    }

    private static double calculatorP99() {
        Collections.sort(timePerRequests);
        float n = timePerRequests.size() * 99 / 100f;
        int index = Math.round(n);
        if (n == Math.round(n)) {
            return (timePerRequests.get(index) + timePerRequests.get(index + 1)) / 2;
        }
        return timePerRequests.get(index) / 2;
    }

    private static double calculatorP95() {
        Collections.sort(timePerRequests);
        float n = timePerRequests.size() * 95 / 100f;
        int index = Math.round(n);
        if (n == Math.round(n)) {
            return (timePerRequests.get(index) + timePerRequests.get(index + 1)) / 2;
        }
        return timePerRequests.get(index) / 2;
    }

    private static void printSystemInfo() {
        System.out.printf("# OS: %s; %s; %s%n",
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"));
        System.out.printf("# JVM: %s; %s%n",
                System.getProperty("java.vendor"),
                System.getProperty("java.version"));
        System.out.printf("# CPU: %d \"procs\"%n",
                Runtime.getRuntime().availableProcessors());
        java.util.Date now = new java.util.Date();
        System.out.printf("# Date: %s%n",
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").format(now));
    }

    private static void runTest() {
        try {
            for (int i = 0; i < threadSize; i++) {
                Future<Report> future = executorService.submit(callables.get(i));
                Report report = future.get();
                reports.add(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void prepareThreadTest() {
        executorService = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            try {
                Callable<Report> callable = new TestCallable(connections / threadSize);
                callables.add(callable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean connectSuccess() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "?n=1")
                .build();
        try {
            okHttpClient.newCall(request).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

// REDIS ONLY
//4 threads and 40000 connections
//        complete: 40000
//        fail: 0
//        mean: 0.22ms
//        +/- stdev: 0.09ms
//        min: 0.13ms
//        max: 34.52ms
//        P95: 0.39ms
//        P99: 1.25ms

// REDIS + GUAVA
//4 threads and 40000 connections
//        complete: 40000
//        fail: 0
//        mean: 0.2ms
//        +/- stdev: 0.06ms
//        min: 0.13ms
//        max: 20.81ms
//        P95: 0.31ms
//        P99: 0.79ms

// REDIS ONLY
//        # OS: Linux; 5.4.0-42-generic; amd64
//        # JVM: Ubuntu; 11.0.8
//        # CPU: 4 "procs"
//        # Date: 2020-08-31 10:22:47+0700
//        Running test http://localhost:4567/prime
//        4 threads and 40000 req
//        complete: 40000
//        fail: 0
//        mean: 0.32ms
//        +/- stdev: 0.2ms
//        min: 0.13ms
//        max: 23.06ms
//        P95: 0.75ms
//        P99: 2.19ms

// REDIS + GUAVA
//        # OS: Linux; 5.4.0-42-generic; amd64
//        # JVM: Ubuntu; 11.0.8
//        # CPU: 4 "procs"
//        # Date: 2020-08-31 10:23:19+0700
//        Running test http://localhost:8080/prime
//        4 threads and 40000 connections
//        complete: 40000
//        fail: 0
//        mean: 0.28ms
//        +/- stdev: 0.09ms
//        min: 0.13ms
//        max: 28.13ms
//        P95: 0.56ms
//        P99: 1.7ms