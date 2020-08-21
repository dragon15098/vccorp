package bai2;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test2 {
    static int bufferSize = 2048;
    static FileReader fileReader;
    static BufferedReader bufferedReader;
    static FileOutputStream fileOutputStream;

    public static void main(String[] args) {
        try {
            fileReader = new FileReader("input.txt");
            bufferedReader = new BufferedReader(fileReader, bufferSize);
            char[] bytes = new char[bufferSize];
            StringBuilder stringBuilder = new StringBuilder();
            while ((bufferedReader.read(bytes, 0, bufferSize)) != -1) {
                stringBuilder.append(new String(bytes).toLowerCase());
            }
            Map<String, Integer> map = new HashMap<>();
            for (String s : stringBuilder.toString().split(" ")) {
                String word = s.trim();
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
            fileOutputStream = new FileOutputStream("output.txt");
            fileOutputStream.write(map.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
