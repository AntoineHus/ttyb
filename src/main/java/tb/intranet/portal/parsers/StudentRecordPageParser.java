package tb.intranet.portal.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tb.intranet.portal.models.AcademicYear;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class StudentRecordPageParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(CoursesPageParser.class);
    private Document document;
    private Elements recordsTables;

    // For caching results
    private ArrayList<AcademicYear> academicYears;
    private ArrayList<Object> academicSemesters;
    private ArrayList<Object> internships;

    public StudentRecordPageParser(String html) {
        document = Jsoup.parse(html);
        recordsTables = findRecordsTables();
    }

    public ArrayList<AcademicYear> getAcademicYears() {
        if (academicYears == null) {
            academicYears = new ArrayList<AcademicYear>();
        } else {
            return academicYears;
        }

        Element resultsTable = recordsTables.get(3);
        Elements rows = resultsTable.select("tr");

        for (int i = 1; i < rows.size(); i++) {
            Elements cols = rows.get(i).select("td");

            AcademicYear academicYear = new AcademicYear();
            academicYear.setSchoolYear(cols.get(0).text());
            academicYear.setProgramme(cols.get(1).text());
            academicYear.setCampus(cols.get(2).text());
            academicYear.setCoursesURL(cols.get(3).select("a").first().attr("href"));

            DateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);

            try {
                academicYear.setStartDate(format.parse(cols.get(6).text()));
            } catch (ParseException e) {
                LOGGER.debug("Unable to parse start date for academic year: " + academicYear.getSchoolYear());
            }

            try {
                academicYear.setEndDate(format.parse(cols.get(7).text()));
            } catch (ParseException e) {
                LOGGER.debug("Unable to parse end date for academic year: " + academicYear.getSchoolYear());
            }

            academicYears.add(academicYear);
        }

        return academicYears;
    }

    public ArrayList<Object> getAcademicSemesters() {
//        Element academicSemestersTable = recordsTables.get(9);
        throw new NotImplementedException();
    }

    public ArrayList<Object> getInternships() {
//        Element internshipsTable      = recordsTables.get(6);
        throw new NotImplementedException();
    }

    private Elements findRecordsTables() {
        return document
                .select("table#rg977")
                .first()
                .select("table");
    }
}
