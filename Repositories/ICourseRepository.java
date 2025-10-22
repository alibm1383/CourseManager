package Repositories;

import Models.Course;

import java.sql.SQLException;
import java.util.List;

public interface ICourseRepository {
    public boolean addCourse(Course course) throws SQLException;
    public List<Course> getTeacherCourses(int teacherId) throws SQLException;
    public boolean removeCourse(int courseId) throws SQLException;
    public boolean hasAvailableCapacity(int courseId) throws SQLException;
    public int getCourseIdByTitle(String title) throws SQLException;
}
