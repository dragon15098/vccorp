package main;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;

public class TestCallable implements Callable<Report> {
    int connections;
    OkHttpClient okHttpClient;
    Random random;

    public TestCallable(int connections) {
        this.connections = connections;
        okHttpClient = new OkHttpClient();
        random = new Random();
    }

    @Override
    public Report call() {
        Report report = new Report();
        doRequest(report);
        return report;
    }

    private void doRequest(Report report) {
        double totalTime = 0.0;
        int reqSuccess = 0, reqFail = 0;
        for (int i = 0; i < connections; i++) {
            int value = random.nextInt(10000) + 1;
            Timer timerPerRequest = new Timer();
            Request request = new Request.Builder()
                    .url("http://localhost:4567/prime?n=" + value)
                    .build();
            Response response;
            try {
                response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    reqSuccess++;
                }
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                reqFail++;
            }
            double timeRequest = timerPerRequest.check();
            if (report.getMax() < timeRequest) report.setMax(timeRequest);
            if (report.getMin() > timeRequest) report.setMin(timeRequest);
            report.addResult(timeRequest);
            totalTime += timeRequest;
        }
        double timePerRequest = totalTime / connections;
        report.setSt(timePerRequest);
        report.setReqSuccess(reqSuccess);
        report.setReqFail(reqFail);
    }
}
