package bai3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class ProcessFileThread implements Callable<Void> {
    String fileName;

    ProcessFileThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Void call() {
        try {
            byte[] bytes = new byte[2048000];
            InputStream inputStream = new FileInputStream(fileName);
            int read = inputStream.read(bytes);
            if (read != -1) {
                String[] s1 = new String(bytes).split(" ");
                for (String s : s1) {
                    String word = s.trim();
                    Test3.addToMap(word);
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
