package tb.intranet.portal.models;

import java.util.Date;

public class AcademicYear {
    private String schoolYear = "";
    private String programme  = "";
    private String campus     = "";
    private String coursesURL = "";
    private Date startDate = new Date();
    private Date endDate   = new Date();

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCoursesURL() {
        return coursesURL;
    }

    public void setCoursesURL(String coursesURL) {
        this.coursesURL = coursesURL;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "AcademicYear{" +
                "schoolYear='" + schoolYear + '\'' +
                ", programme='" + programme + '\'' +
                ", campus='" + campus + '\'' +
                ", coursesURL='" + coursesURL + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
