package v1;

import java.io.Serializable;

public class StudentPoint implements Serializable {
    private long id;
    private float point;
    private String subject;
    private ExamType examType;

    public StudentPoint(long id, float point, String subject, ExamType examType) {
        this.id = id;
        this.point = point;
        this.subject = subject;
        this.examType = examType;
    }

    public StudentPoint(long id, float point, String subject) {
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

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
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
