package tb.intranet.portal.models;

public class Exam {
    private String name = "";
    private Double frenchGrade = 0.00;

    public Exam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
                ", frenchGrade=" + frenchGrade +
                '}';
    }
}
