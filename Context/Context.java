package Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Context {
    private final String url = "your connection string";
    private Connection connection;
    public Context()
    {
        try
        {
            connection = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
