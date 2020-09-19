package v1;

import java.io.Serializable;

public class ExamType implements Serializable {
    private int mul;
    private String type;

    public ExamType(String type) {
        this.type = type;
    }

    public ExamType(int mul) {
        this.mul = mul;
    }

    public ExamType() {
    }

    public ExamType(int mul, String type) {
        this.mul = mul;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamType examType = (ExamType) o;
        return type.equals(examType.type);
    }


    public int getMul() {
        return mul;
    }

    public void setMul(int mul) {
        this.mul = mul;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "v1.ExamType{" +
                "type='" + type + '\'' +
                '}';
    }
}
