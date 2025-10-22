package Repositories;

import Context.Context;
import Models.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements ICourseRepository {
    private final Connection connection ;

    public CourseRepository(Context context)
    {
        connection = context.getConnection();
    }

    public boolean addCourse(Course course) throws SQLException
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Courses (teacherId,title,capacity) VALUES (? , ? , ? )")){
            preparedStatement.setInt(1,course.getTeacherId());
            preparedStatement.setString(2,course.getTitle());
            preparedStatement.setInt(3,course.getCapacity());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean removeCourse(int courseId) throws SQLException
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM Courses WHERE id = ?")){
            preparedStatement.setInt(1,courseId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public int getCourseIdByTitle(String title) throws SQLException
    {

        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM Courses WHERE title = ?"))
        {
            preparedStatement.setString(1,title);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
            return -1;
        }
    }

    public List<Course> getTeacherCourses(int teacherId) throws SQLException
    {
        List<Course> courses =new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Courses WHERE teacherId = ?"))
        {
            preparedStatement.setInt(1,teacherId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course(resultSet.getInt("Id"), resultSet.getString("title"),
                            resultSet.getInt("teacherId"), resultSet.getInt("capacity"));
                    courses.add(course);
                }
            }
            return courses;
        }
    }

    public boolean hasAvailableCapacity(int courseId) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT capacity FROM courses WHERE id = ?")){
            preparedStatement.setInt(1,courseId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int capacity = resultSet.getInt("capacity");
                    if (capacity > 0) {
                        return true;
                    }
                } else {
                    throw new SQLException("The course is not found");
                }
            }
            return false;
        }
    }
}
