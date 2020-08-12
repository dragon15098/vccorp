import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class ProcessFileThread implements Callable<HashMap<String, Integer>> {
    String fileName;

    ProcessFileThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public HashMap<String, Integer> call() {
        try {
            byte[] bytes = new byte[2048];
            HashMap<String, Integer> result = new HashMap<>();
            InputStream inputStream = new FileInputStream(fileName);
            int read = inputStream.read(bytes);
            if (read != -1) {
                String[] s1 = new String(bytes).split(" ");
                for (String s : s1) {
                    String word = s.trim();
                    result.put(word, result.getOrDefault(word, 0) + 1);
                }
            }
            inputStream.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
