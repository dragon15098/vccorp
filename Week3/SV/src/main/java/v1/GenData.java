package v1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class GenData {

    static int[] arrIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//    static String[] nameIds = {"Nguyễn Văn A", "Nguyễn Văn B", "Nguyễn Văn C",
//            "Nguyễn Văn D", "Nguyễn Văn E", "Nguyễn Văn F",
//            "Nguyễn Văn G", "Nguyễn Văn H", "Nguyễn Văn I",
//            "Nguyễn Văn J"};

    static String[] subjects = {"Môn A", "Môn B", "Môn C", "Môn D", "Môn E"};
    static String[] types = {"15 phut", "1 tiet", "cuoi ky"};


    public static void main(String[] args) throws IOException {
        Random random = new Random();
        FileOutputStream fileOutputStream = new FileOutputStream("./data.txt");

        for (int i = 0; i < 100000; i++) {
            String data = "";
            int i1 = random.nextInt(10);
            int randomId = arrIds[i1];
            double randomPoint = random.nextInt(100) / 100.0;
            String randomSubject = subjects[random.nextInt(5)];
            String randomType = types[random.nextInt(3)];
            data = randomId + "\t" + randomPoint + "\t" + randomSubject + "\t" + randomType + "\t\n";
            fileOutputStream.write(data.getBytes());
        }
        fileOutputStream.close();
    }
}
