package bai2;

import bai3.GenData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        try {
            byte[] bytes = new byte[2048];
            InputStream inputStream = new FileInputStream("input.txt");
            int read = inputStream.read(bytes);
            if (read != -1) {
                String[] s1 = new String(bytes).split(" ");
                Map<String, Integer> map = new HashMap<>();
                for (String s : s1) {
                    String word = s.trim();
                    map.put(word, map.getOrDefault(word, 0) + 1);
                }
                FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
                fileOutputStream.write(map.toString().getBytes());
                fileOutputStream.close();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
