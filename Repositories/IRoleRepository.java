package Repositories;

import Models.Role;

import java.sql.SQLException;

public interface IRoleRepository {
    public int getRoleIdByRole(Role role) throws SQLException;
    public Role getRoleByRoleId(int roleId) throws SQLException;
}
