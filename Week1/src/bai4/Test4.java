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
        Point A = new Point(800, 800);
        Point B = new Point(4000, 800);
        Point C = new Point(2400, 2400);

        int distanceToA = 400;
        int distanceToB = 500;
        int distanceToC = 600;
        randomPoint(A, distanceToA, 8000);
        randomPoint(B, distanceToB, 10000);
        randomPoint(C, distanceToC, 12000);
        Collections.shuffle(points);
        FileOutputStream fileOutputStream = new FileOutputStream("output4.txt");
        fileOutputStream.write(points.toString().getBytes());
        fileOutputStream.close();
        System.out.println("GEN point Success. Points size: " + points.size());
    }

    public static void randomPoint(Point srcPoint, int distanceMax, int numberPointNeed) {
        for (int i = 0; i < numberPointNeed / 4; i++) {
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
            points.add(pointUtils.getOppositionPointByX());
            points.add(pointUtils.getOppositionPointByY());
            points.add(pointUtils.getOppositionPointByXAndY());
        }
    }
}
