package bai3;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class ProcessFileThread implements Callable<HashMap<String, Long>> {
    String fileName;
    BufferedInputStream bufferedInputStream;
    FileInputStream fileInputStream;

    ProcessFileThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public HashMap<String, Long> call() {
        try {
            HashMap<String, Long> result = new HashMap<>();
            fileInputStream = new FileInputStream(fileName);
            byte[] bytes = new byte[2048];
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            StringBuilder stringBuilder = new StringBuilder();
            List<byte[]> saveBytes = new ArrayList<>();
            while ((bufferedInputStream.read(bytes)) != -1) {
                saveBytes.add(bytes);
                if (bytes[bytes.length - 1] == ' ') {
                    stringBuilder.append(new String(toBytePrimitive(saveBytes)));
                    String string = convertToString(stringBuilder.toString());
                    countWord(string, result);
                    stringBuilder.delete(0, stringBuilder.length());
                    saveBytes.clear();
                }
                bytes = new byte[2048];
            }
            String last = stringBuilder.toString();
            if (!last.equals("")) {
                String lastString = convertToString(last);
                countWord(lastString, result);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private byte[] toBytePrimitive(List<byte[]> bytes) {
        int sum = bytes.stream().mapToInt(bytes1 -> bytes1.length).sum();
        byte[] result = new byte[sum];
        int j = 0;
        for (byte[] a : bytes) {
            for (byte b : a) {
                result[j] =b;
                j++;
            }
        }
        return result;
    }

    private void countWord(String string, HashMap<String, Long> result) {
        for (String s : string.split(" ")) {
            String word = s.trim();
            result.put(word, result.getOrDefault(word, 0L) + 1);
        }
    }

    private String convertToString(String string) {
        String temp = Normalizer.normalize(string, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        string = pattern
                .matcher(temp).replaceAll("")
                .toLowerCase()
                .replaceAll("đ", "d")
                .replaceAll("[^a-z ]", " ")
                .replaceAll("\\s+", " ");
        return string;
    }
}

//    Bảng chữ cái là một tập hợp các chữ cái - những ký hiệu viết cơ bản hoặc tự vị.