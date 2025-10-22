package Models;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    int id;
    int roleId;
    String fullName;
    String username;
    String hashedPassword;
    Role role;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getRoleId() {
        return roleId;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
    public User(Role role , String fullName, String username , String hashedPassword) {
        this.role = role;
        this.fullName = fullName;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public User(int id ,Role role , String fullName, String username , String hashedPassword )
    {
            this(role,fullName,username,hashedPassword);
            this.id = id;
    }


}