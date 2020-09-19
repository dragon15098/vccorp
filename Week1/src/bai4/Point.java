package bai4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x) {
        this.x = x;
    }

    public double distanceTo(Point point) {
        return Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2));
    }

    public void calculator(Point point, int distance) {
        this.y = ((int) Math.sqrt(Math.pow(distance, 2) - Math.pow(this.x - point.x, 2))) + point.y;
    }

    public static void main(String[] args) {
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(2, 3));
        Point point = new Point(2, 3);
        System.out.println(pointList.contains(point));
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + "\t" + y;
    }
}
