package tb.parsers;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import tb.intranet.portal.models.Course;
import tb.intranet.portal.parsers.CoursesPageParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CoursesPageParserTest {

    @Test
    public void testGetCourses() throws IOException {
        String html = IOUtils.toString(new FileInputStream("src/test/java/tb/parsers/CoursesPage.html"));
        CoursesPageParser coursesPageParser = new CoursesPageParser(html);
        ArrayList<Course> courses = coursesPageParser.getCourses();

        Assert.assertEquals(courses.size(), 14);

        Course course1 = courses.get(0);
        Assert.assertEquals(course1.getCode(), "UVFIP RES110");
        Assert.assertEquals(course1.getSubject(), "Principes fondamentaux des réseaux");
        Assert.assertEquals(course1.getECTSObtainedCredits(), Integer.valueOf(0));
        Assert.assertEquals(course1.getEctsMaximumCredits(), Integer.valueOf(0));
        Assert.assertEquals(course1.getECTSGrade(), "");
        Assert.assertEquals(course1.getFrenchGlobalAverage(), 0.0);
        Assert.assertEquals(course1.getFrenchFinalsAverage(), 0.0);
        Assert.assertEquals(course1.getFrenchIntermediatesAverage(), 0.0);
        Assert.assertEquals(course1.getExamsURL(), "pkg_df.rpt_ele_note_uv.show?p_arg_names=p_id_eleve&p_arg_values=35744&p_arg_names=p_id_up&p_arg_values=16433&p_arg_names=p_id_cursus_suivi&p_arg_values=9");

        Course course9 = courses.get(8);
        Assert.assertEquals(course9.getCode(), "UVFIP SIT130");
        Assert.assertEquals(course9.getSubject(), "Outils mathématiques pour l'ingénieur");
        Assert.assertEquals(course9.getECTSObtainedCredits(), Integer.valueOf(4));
        Assert.assertEquals(course9.getEctsMaximumCredits(), Integer.valueOf(4));
        Assert.assertEquals(course9.getECTSGrade(), "C");
        Assert.assertEquals(course9.getFrenchGlobalAverage(), 13.25);
        Assert.assertEquals(course9.getFrenchFinalsAverage(), 13.0);
        Assert.assertEquals(course9.getFrenchIntermediatesAverage(), 14.28);
        Assert.assertEquals(course9.getExamsURL(), "pkg_df.rpt_ele_note_uv.show?p_arg_names=p_id_eleve&p_arg_values=35744&p_arg_names=p_id_up&p_arg_values=16421&p_arg_names=p_id_cursus_suivi&p_arg_values=9");

    }

}
