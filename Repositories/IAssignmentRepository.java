package Repositories;

import Models.Assignment;

import java.sql.SQLException;
import java.util.List;

public interface IAssignmentRepository {
    public boolean addAssignment(Assignment assignment) throws SQLException;
    public boolean removeAssignment(int assignmentId) throws SQLException;
    public List<Assignment> getAllAssignmentsForCourse(int courseId) throws SQLException;
}
