package Controller;

import Controller.interfaces.callProcedures;
import Controller.interfaces.selectFunctions;

import java.sql.*;

public class DbCon implements callProcedures, selectFunctions {
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
    public boolean testUserLogin(String email) throws SQLException {
        return !new DbCon().getResultSet("select \"E-mail\" from kursach.\"users\" where \"E-mail\" = '" + email + "';").next();
    }

    @Override
    public boolean add_user(String name, String email, String password, int age) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.add_user(?, ?, ?, ?)");
            callableStatement.setString(1,name);
            callableStatement.setString(2,email);
            callableStatement.setString(3,password);
            callableStatement.setInt(4, age);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean create_zakaz(int id_user, boolean dilivery, int[][] dish_number, int[][] addition_number) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.create_zakaz(?, ?, ?, ?)");
            callableStatement.setInt(1,id_user);
            callableStatement.setBoolean(2,dilivery);
            callableStatement.setArray(3,connection.createArrayOf("integer", dish_number));
            callableStatement.setArray(4, connection.createArrayOf("integer", addition_number));
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean create_zakaz(int id_user, boolean dilivery, int[][] dish_number, String type) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.create_zakaz(?, ?, ?, ?)");
            callableStatement.setInt(1,id_user);
            callableStatement.setBoolean(2,dilivery);
            callableStatement.setArray(3,connection.createArrayOf("integer", dish_number));
            callableStatement.setString(4, type);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete_zakaz(int zakaz_id) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.delete_zakaz(?)");
            callableStatement.setInt(1,zakaz_id);

            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert_addition(String name, int num, double cost, String description, int weight) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.insert_addition(?, ?, ?, ?, ?)");
            callableStatement.setString(1,name);
            callableStatement.setInt(2,num);
            callableStatement.setDouble(3,cost);
            callableStatement.setString(4, description);
            callableStatement.setInt(5, weight);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert_dish(String name, int type_id, double cost, int size, int calories, int[][] stucture) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.insert_dish(?, ?, ?, ?, ?, ?)");
            callableStatement.setString(1,name);
            callableStatement.setInt(2,type_id);
            callableStatement.setDouble(3,cost);
            callableStatement.setInt(4, size);
            callableStatement.setInt(5, calories);
            callableStatement.setArray(6, connection.createArrayOf("integer", stucture));
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert_sklad(String cell, String name, int num, Date date, int shelf_life) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.insert_sklad(?, ?, ?, ?, ?)");
            callableStatement.setString(1, cell);
            callableStatement.setString(2, cell);
            callableStatement.setInt(3, num);
            callableStatement.setDate(4, date);
            callableStatement.setInt(5, shelf_life);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert_sklad(String cell, String name, int num, int shelf_life) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.insert_sklad(?, ?, ?, ?)");
            callableStatement.setString(1,cell);
            callableStatement.setString(2,name);
            callableStatement.setInt(3,num);
            callableStatement.setInt(4, shelf_life);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert_type_dish(String name, Time[] feed_time) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.insert_type_dish(?, ?)");
            callableStatement.setString(1,name);
            callableStatement.setArray(2,connection.createArrayOf("kursach.timerange", feed_time));
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update_number_cell(String cell_curr, int number_new) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.update_number_cell(?, ?)");
            callableStatement.setString(1,cell_curr);
            callableStatement.setInt(2, number_new);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update_status_user(int id, String status) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.update_status_user(?, ?)");
            callableStatement.setInt(1, id);
            callableStatement.setString(2,status);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update_status_zakaz(int id, String status) {
        try {
            connection = connect();
            callableStatement = connection.prepareCall(" CALL kursach.update_status_zakaz(?, ?)");
            callableStatement.setInt(1,id);
            callableStatement.setString(2,status);
            callableStatement.execute();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public ResultSet return_dishes_on_type_dish(int type_id) {
        this.resultSet = null;
        return this.resultSet;
    }

    @Override
    public int[][] return_dishes_structure(int type_id) {
        return new int[0][];
    }

    @Override
    public int[][] return_zakaz_addition_composition(int zakaz_id) {
        return new int[0][];
    }

    @Override
    public int[][] return_zakaz_dish_composition(int zakaz_id) {
        return new int[0][];
    }

    @Override
    public ResultSet return_zakaz_user(int user_id) {
        this.resultSet = null;
        return this.resultSet;
    }
}
