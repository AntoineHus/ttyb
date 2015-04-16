package tb.intranet.portal.models;

public class AcademicTerm {
    private AcademicYear academicYear;
    private String name;

    public AcademicTerm(String name) {
        this.name = name;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AcademicTerm{" +
                "academicYear=" + academicYear +
                ", name='" + name + '\'' +
                '}';
    }
}
