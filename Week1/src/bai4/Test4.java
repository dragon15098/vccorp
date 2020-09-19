package bai4;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test4 {

    static List<Point> points = new ArrayList<>();
    static Random random = new Random();

    public static void main(String[] args) throws IOException {
        Point A = new Point(8, 8);
        Point B = new Point(16, 16);
        Point C = new Point(24, 24);

        int distanceToA = 4;
        int distanceToB = 4;
        int distanceToC = 4;
        randomPoint(A, distanceToA, 8);
        randomPoint(B, distanceToB, 10);
        randomPoint(C, distanceToC, 12);
        Collections.shuffle(points);
        FileOutputStream fileOutputStream = new FileOutputStream("output4.txt");
        for (Point point : points) {
            fileOutputStream.write((point.toString()+ "\n").getBytes());
        }
        fileOutputStream.close();
        System.out.println("GEN point Success. Points size: " + points.size());
    }

    public static void randomPoint(Point srcPoint, int distanceMax, int numberPointNeed) {
        for (int i = 0; i < numberPointNeed; i++) {
            int range = srcPoint.x - distanceMax;
            int x = random.nextInt(distanceMax) + range;
            Point tempPoint = new Point(x);
            tempPoint.calculator(srcPoint, distanceMax);
            while (tempPoint.distanceTo(srcPoint) > distanceMax || points.contains(tempPoint)) {
                x = random.nextInt(distanceMax) + range;
                tempPoint = new Point(x);
                tempPoint.calculator(srcPoint, distanceMax);
            }
            if(tempPoint.distanceTo(tempPoint)> distanceMax){
                System.out.println("FALSE");
            }
            PointUtils pointUtils = new PointUtils(tempPoint, srcPoint);
            points.add(tempPoint);
//            points.add(pointUtils.getOppositionPointByX());
//            points.add(pointUtils.getOppositionPointByY());
//            points.add(pointUtils.getOppositionPointByXAndY());
        }
    }
}
