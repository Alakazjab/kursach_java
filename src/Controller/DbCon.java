package Controller;

import java.sql.*;

public class DbCon {
    static Connection connection;
    Statement statement;
    ResultSet resultSet;
    static CallableStatement callableStatement;
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://172.20.8.15:5432/db0901_08","st0901","pwd0901");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getResultSet(String query) throws SQLException {
        connection = connect();
        this.statement = connection.createStatement();
        this.resultSet = this.statement.executeQuery(query);
        return this.resultSet;
    }
    public void executeQuery(String query) throws SQLException {
        this.statement.executeUpdate(query);
    }
    public boolean createUser(String lodin, String password) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL \"createUser\"(?,?) ;");
            callableStatement.setString(1,lodin);
            callableStatement.setString(2,password);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean testUserLogin(String email) throws SQLException {
        return !new DbCon().getResultSet("select \"E-mail\" from kursach.\"users\" where \"E-mail\" = '" + email + "';").next();
    }
}
