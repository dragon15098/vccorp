
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Info;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpSender implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSender.class);

    public void run() {
        while (true) {
            try {
                requestGet();
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void requestGet() throws IOException {
        Request request = new Request.Builder()
                .url("http://news.admicro.vn:10002/api/realtime?domain=kenh14.vn")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            LOGGER.info("GET Request success");
            ObjectMapper objectMapper = new ObjectMapper();
            String bodyData = response.body().string();
            Info info = objectMapper.readValue(bodyData, Info.class);
            Test1.oldValue = Test1.value;
            Test1.value = info.getUser();
        }
    }
}
