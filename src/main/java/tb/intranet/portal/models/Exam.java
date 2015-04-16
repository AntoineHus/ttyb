package tb.intranet.portal.models;

public class Exam {
    private String name;
    private Course course;
    private Double frenchGrade = 0.00;

    public Exam(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getFrenchGrade() {
        return frenchGrade;
    }

    public void setFrenchGrade(Double frenchGrade) {
        this.frenchGrade = frenchGrade;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "name='" + name + '\'' +
                ", course=" + course +
                ", frenchGrade=" + frenchGrade +
                '}';
    }
}
