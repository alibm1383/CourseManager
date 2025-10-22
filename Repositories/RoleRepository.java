package Repositories;

import Context.Context;
import Models.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepository implements IRoleRepository{

    private final Connection connection;
    RoleRepository(Context context)
    {
        connection = context.getConnection();
    }
    public int getRoleIdByRole(Role role) throws SQLException
    {
        String sql = "SELECT id FROM Roles WHERE role = ? ";
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1,role.toString());
            try(ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) {
                    return rs.getInt("Id");
                }
                return -1;
            }
        }
    }


    public Role getRoleByRoleId(int roleId) throws SQLException
    {
        Role role = null;
        String sql = "SELECT role FROM Roles WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,roleId);
            try(ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) {
                    String roleString = rs.getString("role");
                    switch (roleString.toLowerCase()){
                        case "admin" : role = Role.Admin;
                            break;
                        case "teacher" : role = Role.Teacher;
                        break;
                        case "student" : role = Role.Student;
                        break;
                    }
                    return role;
                }
                return null;
            }
        }
    }



}
