package tb.cli;

import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;
import tb.intranet.cas.exceptions.InvalidCredentialsException;
import tb.intranet.cas.exceptions.UserNotLoggedException;
import tb.intranet.portal.Portal;
import tb.intranet.portal.models.AcademicYear;
import tb.intranet.portal.models.Course;

import java.util.ArrayList;

public class CoursesOutput {
    private Portal portal;
    private String username;
    private String password;

    public CoursesOutput(WebDriver driver, String username, String password) {
        this.portal = new Portal(driver);
        this.username = username;
        this.password = password;
    }

    private ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();

        try {
            System.out.println("Authenticating with CAS...");
            portal.login(username, password);
        } catch (InvalidCredentialsException e) {
            System.err.println("Invalid username and/or password.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unknown error while login to portal.");
            System.exit(1);
        }

        try {
            System.out.println("Parsing courses...");
            AcademicYear academicYear = portal.getAcademicYears().get(0);
            courses = portal.getCourses(academicYear);
        } catch (UserNotLoggedException e) {
            System.err.println("User is not logged.");
            System.exit(1);
        }

        return courses;
    }

    public String toString() {
        String output = "";

        String[] header = { "Course", "Grade", "Average (/20)" };
        output += String.format("%-15s%-8s%-20s\n", header);

        for (Course course : getCourses()) {
            if (course.getECTSGrade().isEmpty()) { continue; }
            String[] row = { course.getCode(), course.getECTSGrade(), String.valueOf(course.getFrenchGlobalAverage()) };
            output += String.format("%-15s%-8s%-20s\n", row);
        }

        return  output;
    }

    public String toString(boolean json) {
        if (json) {
            Gson gson = new Gson();
            return gson.toJson(getCourses());
        } else {
            return toString();
        }
    }
}
