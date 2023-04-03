package Model;

import Model.interfaces.callProcedures;
import Model.interfaces.selectFunctions;

import java.sql.*;

public class DbCon implements callProcedures, selectFunctions {
    static Connection connection;
    Statement statement;
    ResultSet resultSet;
    static CallableStatement callableStatement;
    static PreparedStatement preparedStatement;
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://172.20.8.15:5432/db0901_08","st0901","pwd0901");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getResultSet(String query) throws SQLException {
        connection = connect();
        this.statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
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
    public boolean add_user(String name, String email, String password, int age) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.add_user(?, ?, ?, ?)");
        callableStatement.setString(1,name);
        callableStatement.setString(2,email);
        callableStatement.setString(3,password);
        callableStatement.setInt(4, age);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean create_zakaz(int id_user, boolean dilivery, Object[][] dish_number, Object[][] addition_number) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.create_zakaz(?, ?, ?, ?)");
        callableStatement.setInt(1,id_user);
        callableStatement.setBoolean(2,dilivery);
        callableStatement.setArray(3,connection.createArrayOf("integer", dish_number));
        callableStatement.setArray(4, connection.createArrayOf("integer", addition_number));
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean create_zakaz(int id_user, boolean dilivery, Object[][] dish_number, String type) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.create_zakaz(?, ?, ?, ?)");
        callableStatement.setInt(1,id_user);
        callableStatement.setBoolean(2,dilivery);
        callableStatement.setArray(3,connection.createArrayOf("integer", dish_number));
        callableStatement.setString(4, type);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean delete_zakaz(int zakaz_id) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.delete_zakaz(?)");
        callableStatement.setInt(1,zakaz_id);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean insert_addition(String name, int num, double cost, String description, int weight) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.insert_addition(?, ?, ?, ?, ?)");
        callableStatement.setString(1,name);
        callableStatement.setInt(2,num);
        callableStatement.setDouble(3,cost);
        callableStatement.setString(4, description);
        callableStatement.setInt(5, weight);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean insert_dish(String name, int type_id, double cost, int size, int calories, Object [][] stucture) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.insert_dish(?, ?, ?, ?, ?, ?)");
        callableStatement.setString(1,name);
        callableStatement.setInt(2,type_id);
        callableStatement.setDouble(3,cost);
        callableStatement.setInt(4, size);
        callableStatement.setInt(5, calories);
        callableStatement.setArray(6, connection.createArrayOf("integer", stucture));
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean insert_sklad(String cell, String name, int num, Date date, int shelf_life) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.insert_sklad(?, ?, ?, ?, ?)");
        callableStatement.setString(1, cell);
        callableStatement.setString(2, cell);
        callableStatement.setInt(3, num);
        callableStatement.setDate(4, date);
        callableStatement.setInt(5, shelf_life);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean insert_sklad(String cell, String name, int num, int shelf_life) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.insert_sklad(?, ?, ?, ?)");
        callableStatement.setString(1,cell);
        callableStatement.setString(2,name);
        callableStatement.setInt(3,num);
        callableStatement.setInt(4, shelf_life);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean insert_type_dish(String name, Time feed_time_start, Time feed_time_end) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.insert_type_dish(?, ?, ?)");
        callableStatement.setString(1,name);
        callableStatement.setTime(2, feed_time_start);
        callableStatement.setTime(3, feed_time_end);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean update_number_cell(String cell_curr, int number_new) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.update_number_cell(?, ?)");
        callableStatement.setString(1,cell_curr);
        callableStatement.setInt(2, number_new);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean update_status_user(int id, String status) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.update_status_user(?, ?)");
        callableStatement.setInt(1, id);
        callableStatement.setString(2,status);
        callableStatement.execute();
        return true;
    }

    @Override
    public boolean update_status_zakaz(int id, String status) throws SQLException {
        connection = connect();
        callableStatement = connection.prepareCall(" CALL kursach.update_status_zakaz(?, ?)");
        callableStatement.setInt(1,id);
        callableStatement.setString(2,status);
        callableStatement.execute();
        return true;
    }

    @Override
    public ResultSet return_dishes_on_type_dish(int type_id) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.return_dishes_on_type_dish(?)");
        preparedStatement.setInt(1,type_id);
        this.resultSet = preparedStatement.executeQuery();
        return this.resultSet;
    }

    @Override
    public int[][] return_dishes_structure(int type_id) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.return_dishes_structure(?)");
        preparedStatement.setInt(1,type_id);
        preparedStatement.execute();
        this.resultSet = preparedStatement.getResultSet();
        this.resultSet.next();
        Array result = this.resultSet.getArray(1);
        return (int[][]) result.getArray();
    }

    @Override
    public int[][] return_zakaz_addition_composition(int zakaz_id) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.return_zakaz_addition_composition(?)");
        preparedStatement.setInt(1,zakaz_id);
        preparedStatement.execute();
        this.resultSet = preparedStatement.getResultSet();
        this.resultSet.next();
        Array result = this.resultSet.getArray(1);
        return (int[][]) result.getArray();
    }

    @Override
    public int[][] return_zakaz_dish_composition(int zakaz_id) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.return_zakaz_dish_composition(?)");
        preparedStatement.setInt(1,zakaz_id);
        preparedStatement.execute();
        this.resultSet = preparedStatement.getResultSet();
        this.resultSet.next();
        Array result = this.resultSet.getArray(1);
        return (int[][]) result.getArray();
    }

    @Override
    public ResultSet return_zakaz_user(int user_id) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.return_zakaz_user(?)");
        preparedStatement.setInt(1,user_id);
        this.resultSet = preparedStatement.executeQuery();
        return this.resultSet;
    }

    @Override
    public String return_user_status(String email) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.return_user_status(?)");
        preparedStatement.setString(1,email);
        preparedStatement.execute();
        this.resultSet = preparedStatement.getResultSet();
        this.resultSet.next();
        return this.resultSet.getString(1);
    }

    @Override
    public String auth_user(String email) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.auth_user(?)");
        preparedStatement.setString(1,email);
        preparedStatement.execute();
        this.resultSet = preparedStatement.getResultSet();
        this.resultSet.next();
        return this.resultSet.getString(1);
    }

    @Override
    public int return_user_id(String email) throws SQLException {
        connection = connect();
        preparedStatement = connection.prepareCall(" select kursach.return_user_id(?)");
        preparedStatement.setString(1,email);
        preparedStatement.execute();
        this.resultSet = preparedStatement.getResultSet();
        this.resultSet.next();
        return this.resultSet.getInt(1);
    }
}
