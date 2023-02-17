package db;

import src.utils.IProperties;
import src.utils.PropertiesConfig;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Map;

public class MySqlExecutor implements db.IDBExecutor {

    private static Connection connection = null;
    public static Statement statement = null;

    private void createDbStatement() {
        IProperties propertiesReader = new PropertiesConfig();
        Map<String, String> propertie = propertiesReader.read();
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(propertie.get("url") + String.format("/%s", propertie.get("db_name")), propertie.get("username"), propertie.get("password"));
            }
            if (statement == null || statement.isClosed()) {
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            throw new RuntimeException((e));
        }
    }


    @Override
    public void close() {
        try {
            if (statement != null && !statement.isClosed())
                statement.close();
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public void execute(String request) {
        createDbStatement();
        try {
            statement.execute(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet executeQuery(String request) {
        createDbStatement();
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery(request);
            int columns = resultSet.getMetaData().getColumnCount();
            // Перебор строк с данными
            while(resultSet .next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
            }
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

