package tb.intranet.portal;

import org.openqa.selenium.WebDriver;
import tb.intranet.cas.CAS;
import tb.intranet.cas.exceptions.UserNotLoggedException;
import tb.intranet.portal.models.AcademicYear;
import tb.intranet.portal.models.Course;
import tb.intranet.portal.models.Exam;
import tb.intranet.portal.parsers.CoursesPageParser;
import tb.intranet.portal.parsers.ExamsPageParser;
import tb.intranet.portal.parsers.StudentRecordPageParser;

import java.util.ArrayList;

/**
 * Implementation of the portal interface using
 * web browser automation with Selenium.
 */
public class WebDriverPortal extends CAS implements Portal {
    private final String baseURL = "https://portail.telecom-bretagne.eu";

    // For caching
    private StudentRecordPageParser studentRecordPageParser;

    public WebDriverPortal(WebDriver driver) {
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
        ExamsPageParser examsPageParser = new ExamsPageParser(driver.getPageSource());

        return examsPageParser.getExams();
    }

    private StudentRecordPageParser getStudentRecordPageParser() {
        if (studentRecordPageParser == null) {
            driver.get(baseURL + "/portal/page/portal/FORMATION/ELEVE");
            studentRecordPageParser = new StudentRecordPageParser(driver.getPageSource());
        }
        return studentRecordPageParser;
    }
}
