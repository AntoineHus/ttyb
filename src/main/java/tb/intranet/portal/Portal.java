package tb.intranet.portal;

import tb.intranet.cas.exceptions.UserNotLoggedException;
import tb.intranet.portal.models.AcademicYear;
import tb.intranet.portal.models.Course;
import tb.intranet.portal.models.Exam;

import java.util.List;

/**
 * Interface to Télécom Bretagne student portal.
 */
public interface Portal {
    /**
     * Fetch all academic years.
     * @return A List of AcademicYear.
     * @throws UserNotLoggedException if the user is not logged.
     */
    public List<AcademicYear> getAcademicYears() throws UserNotLoggedException;

    /**
     * Fetch all courses for a given academic year.
     * @param academicYear
     * @return A List of Course.
     * @throws UserNotLoggedException if the user is not logged.
     */
    public List<Course> getCourses(AcademicYear academicYear) throws UserNotLoggedException;

    /**
     * Fetch all exams for a given course.
     * @param course
     * @return A list of Exam.
     * @throws UserNotLoggedException if the user is not logged.
     */
    public List<Exam> getExams(Course course) throws UserNotLoggedException;
}

