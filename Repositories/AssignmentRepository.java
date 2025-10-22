package Repositories;

import Context.Context;
import Models.Assignment;
import Models.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentRepository implements IAssignmentRepository {
    private final Connection connection ;

    public AssignmentRepository(Context context)
    {
        connection = context.getConnection();
    }
    public boolean addAssignment(Assignment assignment) throws SQLException
    {
        try (PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO Assignments (courseId , title) VALUES (? , ?)"))
        {
            ps.setInt(1,assignment.getCourseId());
            ps.setString(2,assignment.getTitle());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeAssignment(int assignmentId) throws SQLException
    {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Assignments WHERE id = ?")){
            ps.setInt(1,assignmentId);
            return ps.executeUpdate() > 0;
        }
    }



    public List<Assignment> getAllAssignmentsForCourse(int courseId) throws SQLException
    {
        List<Assignment> assignments = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM Assignments WHERE courseId  = ?"))
        {
            ps.setInt(1,courseId);
            try(ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Assignment assignment = new Assignment
                            (resultSet.getInt("id"),
                                    resultSet.getInt("courseId"),
                                    resultSet.getString("title"));

                    assignments.add(assignment);
                }
            }
            return assignments;
        }
    }
}
