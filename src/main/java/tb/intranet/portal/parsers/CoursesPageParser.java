package tb.intranet.portal.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tb.intranet.portal.models.Course;

import java.util.ArrayList;

public class CoursesPageParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(CoursesPageParser.class);
    private Document document;

    // For caching results
    private ArrayList<Course> courses;

    public CoursesPageParser(String html) {
        document = Jsoup.parse(html);
    }

    public ArrayList<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<Course>();
        } else {
            return courses;
        }

        Element table = document.select("table").get(2);
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) {
            Elements cols = rows.get(i).select("td");

            // Parse course code & subject, to initialize the Course
            String code = cols.get(3).text();
            String subject = cols.get(4).text();
            Course course = new Course(code, subject);

            // Parse exams url
            course.setExamsURL(cols.get(4).select("a").first().attr("href"));

            // Parse ECTS credits
            try {
                String[] totalCredits = cols.get(5).text().split("/");
                course.setECTSObtainedCredits(Integer.parseInt(totalCredits[0]));
                course.setECTSMaximumCredits(Integer.parseInt(totalCredits[1]));
            } catch (Exception e) {
                LOGGER.debug("Unable to parse ECTS credits for: " + code);
            }

            // Parse ECTS grade
            String ectsGrade = cols.get(6).text();
            if (ectsGrade.length() > 1) {
                ectsGrade = "";
            }
            course.setECTSGrade(ectsGrade);

            // Parse french grades
            try {
                course.setFrenchGlobalAverage(Double.parseDouble(cols.get(7).text()));
            } catch (Exception e) {
                LOGGER.debug("Unable to parse french global average for: " + code);
            }

            try {
                course.setFrenchFinalsAverage(Double.parseDouble(cols.get(8).text()));
            } catch (Exception e) {
                LOGGER.debug("Unable to parse french finals average for: " + code);
            }

            try {
                course.setFrenchIntermediatesAverage(Double.parseDouble(cols.get(9).text()));
            } catch (Exception e) {
                LOGGER.debug("Unable to parse french intermediates average for: " + code);
            }

            courses.add(course);
        }

        return courses;
    }
}
