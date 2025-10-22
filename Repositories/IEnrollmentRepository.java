package Repositories;

import Models.Course;
import Models.User;

import java.sql.SQLException;
import java.util.List;

public interface IEnrollmentRepository {
    public boolean addStudentToCourse(int studentId,int courseId) throws SQLException;
    public boolean removeStudentFromCourse(int studentId,int courseId) throws SQLException;
    public List<User> getCourseStudents(int courseId) throws SQLException;
    public boolean addPoint(int studentId,int courseId , float point) throws SQLException;
}
