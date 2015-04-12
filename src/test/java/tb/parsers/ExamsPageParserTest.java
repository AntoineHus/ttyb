package tb.parsers;

import org.testng.Assert;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import tb.intranet.portal.models.Exam;
import tb.intranet.portal.parsers.ExamsPageParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExamsPageParserTest {

    @Test
    public void testGetExams() throws IOException {
        String html = IOUtils.toString(new FileInputStream("src/test/java/tb/parsers/ExamsPage.html"));
        ExamsPageParser examsPageParser = new ExamsPageParser(html);
        ArrayList<Exam> exams = examsPageParser.getExams();

        Assert.assertEquals(exams.size(), 4);

        Exam exam1 = exams.get(0);
        Assert.assertEquals(exam1.getName(), "CC1");
        Assert.assertEquals(exam1.getFrenchGrade(), 18.0);

        Exam exam2 = exams.get(1);
        Assert.assertEquals(exam2.getName(), "CC2");
        Assert.assertEquals(exam2.getFrenchGrade(), 19.2);
    }
}
