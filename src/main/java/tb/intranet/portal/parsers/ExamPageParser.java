package tb.intranet.portal.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tb.intranet.portal.models.Exam;

import java.util.ArrayList;

public class ExamPageParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(CoursesPageParser.class);
    private Document document;

    // For caching results
    private ArrayList<Exam> exams;

    public ExamPageParser(String html) {
        document = Jsoup.parse(html);
    }

    public ArrayList<Exam> getExams() {
        if (exams == null) {
            exams = new ArrayList<Exam>();
        } else {
            return exams;
        }

        Element table = document.select("table").get(0);
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) {
            Elements cols = rows.get(i).select("td");

            String name = cols.get(3).text();
            Exam exam = new Exam(name);

            try {
                exam.setFrenchGrade(Double.parseDouble(cols.get(7).text()));
            } catch (Exception e) {
                LOGGER.debug("Unable to parse grade for exam: " + name);
            }

            exams.add(exam);
        }

        return exams;
    }
}
