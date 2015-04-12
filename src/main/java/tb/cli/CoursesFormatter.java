package tb.cli;

import com.google.gson.Gson;
import tb.intranet.portal.models.Course;

import java.util.ArrayList;

public class CoursesFormatter {
    ArrayList<Course> courses;

    public CoursesFormatter(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String toString() {
        String output = "";

        String[] header = { "Course", "Grade", "Average (/20)" };
        output += String.format("%-15s%-8s%-20s\n", header);

        for (Course course : courses) {
            if (course.getECTSGrade().isEmpty()) { continue; }
            String[] row = { course.getCode(), course.getECTSGrade(), String.valueOf(course.getFrenchGlobalAverage()) };
            output += String.format("%-15s%-8s%-20s\n", row);
        }

        return  output;
    }

    public String toString(boolean json) {
        if (json) {
            Gson gson = new Gson();
            return gson.toJson(courses);
        } else {
            return toString();
        }
    }
}
