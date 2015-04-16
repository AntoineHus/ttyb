package tb.intranet.portal.models;

public class Course {
    // Course description
    private String code;
    private String subject;
    private AcademicTerm academicTerm;

    // ECTS system
    private Integer ectsMaximumCredits = 0;
    private Integer ectsObtainedCredits = 0;
    private String  ectsGrade = "";

    // French grading system
    private Double frenchGlobalAverage = 0.00;
    private Double frenchFinalsAverage = 0.00;
    private Double frenchIntermediatesAverage = 0.00;

    // Exams
    private String examsURL;

    public Course(String code, String subject) {
        this.code = code;
        this.subject = subject;
    }

    public String getCode() {
        return code;
    }

    public String getSubject() {
        return subject;
    }

    public AcademicTerm getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(AcademicTerm academicTerm) {
        this.academicTerm = academicTerm;
    }

    public Integer getEctsMaximumCredits() {
        return ectsMaximumCredits;
    }

    public void setECTSMaximumCredits(Integer ectsMaximumCredits) {
        this.ectsMaximumCredits = ectsMaximumCredits;
    }

    public Integer getECTSObtainedCredits() {
        return ectsObtainedCredits;
    }

    public void setECTSObtainedCredits(Integer ectsObtainedCredits) {
        this.ectsObtainedCredits = ectsObtainedCredits;
    }

    public String getECTSGrade() {
        return ectsGrade;
    }

    public void setECTSGrade(String ectsGrade) {
        this.ectsGrade = ectsGrade;
    }

    public Double getFrenchGlobalAverage() {
        return frenchGlobalAverage;
    }

    public void setFrenchGlobalAverage(Double frenchGlobalAverage) {
        this.frenchGlobalAverage = frenchGlobalAverage;
    }

    public Double getFrenchFinalsAverage() {
        return frenchFinalsAverage;
    }

    public void setFrenchFinalsAverage(Double frenchFinalsAverage) {
        this.frenchFinalsAverage = frenchFinalsAverage;
    }

    public Double getFrenchIntermediatesAverage() {
        return frenchIntermediatesAverage;
    }

    public void setFrenchIntermediatesAverage(Double frenchIntermediatesAverage) {
        this.frenchIntermediatesAverage = frenchIntermediatesAverage;
    }

    public String getExamsURL() {
        return examsURL;
    }

    public void setExamsURL(String examsURL) {
        this.examsURL = examsURL;
    }

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", subject='" + subject + '\'' +
                ", academicTerm=" + academicTerm +
                ", ectsMaximumCredits=" + ectsMaximumCredits +
                ", ectsObtainedCredits=" + ectsObtainedCredits +
                ", ectsGrade='" + ectsGrade + '\'' +
                ", frenchGlobalAverage=" + frenchGlobalAverage +
                ", frenchFinalsAverage=" + frenchFinalsAverage +
                ", frenchIntermediatesAverage=" + frenchIntermediatesAverage +
                ", examsURL='" + examsURL + '\'' +
                '}';
    }
}
