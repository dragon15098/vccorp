package bai3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class GenData {

    static Random random = new Random();
    static int totalString = 0;

    private GenData() {
    }

    public static void genData() throws IOException {
        int numberString;
        for (int index = 1; index <= 10; index++) {
            StringBuilder stringBuilder = new StringBuilder();
            numberString = random.nextInt(1000)+1;
            totalString += numberString;
            for (int j = 0; j < numberString; j++) {
                int stringLength = random.nextInt(2) + 1;
                stringBuilder.append(randomString(stringLength)).append(" ");
            }
            FileOutputStream fileOutputStream = new FileOutputStream("data/file" + index + ".txt");
            fileOutputStream.write(stringBuilder.toString().trim().getBytes());
            fileOutputStream.close();
        }
    }

    private static String randomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append((char) (random.nextInt(25) + 65));
        }
        return result.toString();
    }
}
