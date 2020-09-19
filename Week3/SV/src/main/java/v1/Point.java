package v1;

import java.io.Serializable;

public class Point implements Serializable {
    public float value;
    public int mul;
    public float lastPoint;

    public Point(float value, int mul) {
        this.value = value;
        this.mul = mul;
    }

    @Override
    public String toString() {
        return "v1.Point{" +
                "value=" + value +
                ", mul=" + mul +
                ", lastPoint=" + lastPoint +
                '}';
    }
}
