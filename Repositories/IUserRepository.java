package Repositories;


import Models.Role;
import Models.User;

import java.sql.SQLException;

public interface IUserRepository {

    public boolean addUser(User user) throws SQLException;
    public boolean removeUser(int userId) throws SQLException;
    public int getUserIdByUsername(String username) throws SQLException;
    public boolean isAnyAdminExist() throws SQLException;
    public User getUserByUsername(String username) throws SQLException;
    public User authenticateUser (Role role , String username , String password) throws SQLException;
}
