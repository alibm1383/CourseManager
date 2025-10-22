package Repositories;

import Context.Context;
import Models.Role;
import Models.User;
import Utilities.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements  IUserRepository{
    private final Connection connection;
    private  IRoleRepository roleRepository;

    public UserRepository(Context context)
    {
        connection = context.getConnection();
        roleRepository = new RoleRepository(context);
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (roleId,fullName,username,hashedPassword) VALUES (?, ? , ? , ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,roleRepository.getRoleIdByRole(user.getRole()));
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getHashedPassword());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeUser(int userId) throws SQLException
    {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Users WHERE id = ?")){
            ps.setInt(1,userId);
            return ps.executeUpdate() > 0;
        }
    }

    public int getUserIdByUsername(String username) throws SQLException
    {
        String sql = "SELECT id FROM Users WHERE username = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,username);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Id");
                }
                else {
                    return -1;
                }
            }
        }
    }


    public User getUserByUsername(String username)throws SQLException
    {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,username);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Role role = roleRepository.getRoleByRoleId(rs.getInt("roleId"));
                    User user = new User(rs.getInt("Id"),role,rs.getString("fullName"),
                            rs.getString("username") , rs.getString("hashedPassword"));
                    return user;
                }
                else {
                    return null;
                }
            }
        }
    }


    @Override
    public boolean isAnyAdminExist() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt " +
                "FROM Users u " +
                "JOIN Roles r ON u.roleId = r.id " +
                "WHERE r.role = 'Admin'";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt") > 0;
                }
            }
            return false;
        }

    }

    public User authenticateUser (Role role ,String username , String password) throws SQLException
    {
        User user = getUserByUsername(username);
        if (user == null)
        {
            return null;
        }
        else if(PasswordUtils.checkPassword(password,user.getHashedPassword()) && user.getRole() == role){
            return user;
        }
        return null;
    }
}
