package Repositories;

import Context.Context;
import Models.Course;
import Models.Role;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository implements IEnrollmentRepository {
    private final Connection connection;

    public EnrollmentRepository(Context context)
    {
        connection = context.getConnection();
    }

    public boolean addStudentToCourse(int studentId,int courseId) throws SQLException
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO Enrollments (studentId,courseId) VALUES (? , ?)"))
        {
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            return preparedStatement.executeUpdate() > 0;
        }
    }


    public boolean removeStudentFromCourse(int studentId,int courseId) throws SQLException
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement
                ("DELETE FROM Enrollments WHERE studentId = ? AND courseId = ?"))
        {
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            return preparedStatement.executeUpdate() > 0;
        }
    }


    public List<User> getCourseStudents(int courseId) throws SQLException
    {
        List<User> students = new ArrayList<>();
        String sql = "SELECT u.id , u.fullName, u.username, u.hashedPassword " +
                "FROM Users u " +
                "JOIN Enrollments e ON u.id = e.studentId " +
                "WHERE e.courseId = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,courseId);
            try(ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    User student = new User(rs.getInt("Id"),Role.Student,
                            rs.getString("fullName"),rs.getString("username"),
                            rs.getString("hashedPassword"));
                    students.add(student);
                }
            }
        }
        return students;
    }

    public boolean addPoint(int studentId,int courseId , float point) throws SQLException
    {
        String sql = "UPDATE Enrollments SET point = ? WHERE studentId = ? AND courseId = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setFloat(1,point);
            preparedStatement.setInt(2,studentId);
            preparedStatement.setInt(3,courseId);
            return preparedStatement.executeUpdate() > 0;
        }
    }


}
