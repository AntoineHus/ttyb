package tb.intranet.portail;

import org.openqa.selenium.WebDriver;
import tb.intranet.cas.CAS;
import tb.intranet.cas.exceptions.UserNotLoggedException;
import tb.intranet.portail.models.AcademicYear;
import tb.intranet.portail.models.Course;
import tb.intranet.portail.models.Exam;
import tb.intranet.portail.parsers.CoursesPageParser;
import tb.intranet.portail.parsers.ExamPageParser;
import tb.intranet.portail.parsers.StudentRecordPageParser;

import java.util.ArrayList;

public class Portal extends CAS {
    private final String baseURL = "https://portail.telecom-bretagne.eu";

    // For caching
    private StudentRecordPageParser studentRecordPageParser;

    public Portal(WebDriver driver) {
        super(driver);
    }

    public void getGPA() throws UserNotLoggedException {
        ensureUserIsLogged();
    }

    public ArrayList<AcademicYear> getAcademicYears() throws UserNotLoggedException {
        ensureUserIsLogged();
        return getStudentRecordPageParser().getAcademicYears();
    }

    public ArrayList<Course> getCourses(AcademicYear academicYear) throws UserNotLoggedException {
        ensureUserIsLogged();

        driver.get(baseURL + "/portal/pls/portal/" + academicYear.getCoursesURL());
        CoursesPageParser coursesPageParser = new CoursesPageParser(driver.getPageSource());

        return coursesPageParser.getCourses();
    }

    public ArrayList<Exam> getExams(Course course) throws UserNotLoggedException {
        ensureUserIsLogged();

        driver.get(baseURL + "/portal/pls/portal/" + course.getExamsURL());
        ExamPageParser examPageParser = new ExamPageParser(driver.getPageSource());

        return examPageParser.getExams();
    }

    private StudentRecordPageParser getStudentRecordPageParser() {
        if (studentRecordPageParser == null) {
            driver.get(baseURL + "/portal/page/portal/FORMATION/ELEVE");
            studentRecordPageParser = new StudentRecordPageParser(driver.getPageSource());
        }
        return studentRecordPageParser;
    }
}