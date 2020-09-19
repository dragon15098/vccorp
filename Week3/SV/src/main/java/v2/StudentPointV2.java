package v2;

import v1.ExamType;

public class StudentPointV2 {
    private long id;
    private float point;
    private String subject;
    private ExamType examType;
    private int mul;
        private float lastPoint;

    public StudentPointV2(long id, float point, String subject, ExamType examType) {
        this.id = id;
        this.point = point;
        this.subject = subject;
        this.examType = examType;
        this.mul = examType.getMul();
        this.lastPoint = this.mul * this.point;
    }

    public StudentPointV2(long id, float point, String subject) {
        this.id = id;
        this.point = point;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMul() {
        return mul;
    }

    public float getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(float lastPoint) {
        this.lastPoint = lastPoint;
    }

    public void setMul(int mul) {
        this.mul = mul;
        this.examType = new ExamType(mul);
    }

    @Override
    public String toString() {
        return "v1.StudentPoint{" +
                "id=" + id +
                ", point=" + point +
                ", subject='" + subject + '\'' +
                ", type=" + examType +
                '}';
    }
}
