package bai4;

public class PointUtils {

    Point srcPoint;
    Point desPoint;

    private PointUtils() {
    }

    public PointUtils(Point srcPoint, Point desPoint) {
        this.srcPoint = srcPoint;
        this.desPoint = desPoint;
    }

    public Point getOppositionPointByX() {
        return new Point(getOppositionX(), srcPoint.y);
    }

    public Point getOppositionPointByY() {
        return new Point(srcPoint.x, getOppositionY());
    }

    public Point getOppositionPointByXAndY() {
        return new Point(getOppositionX(), getOppositionY());
    }

    private int getOppositionX() {
        int distanceX = srcPoint.x - desPoint.x;
        if (distanceX < 0) {
            return desPoint.x - Math.abs(distanceX);
        } else {
            return desPoint.x + Math.abs(distanceX);
        }
    }

    private int getOppositionY() {
        int distanceY = srcPoint.y - desPoint.y;
        if (distanceY < 0) {
            return desPoint.y - Math.abs(distanceY);
        } else {
            return desPoint.y + Math.abs(distanceY);
        }
    }
}
