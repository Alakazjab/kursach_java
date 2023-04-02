package Model.interfaces;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public interface callProcedures {
    boolean add_user(String name, String email, String password, int age) throws SQLException;
    boolean create_zakaz(int id_user, boolean dilivery, Object[][] dish_number, Object[][] addition_number) throws SQLException;
    boolean create_zakaz(int id_user, boolean dilivery, Object[][] dish_number, String type) throws SQLException;
    boolean delete_zakaz(int zakaz_id) throws SQLException;
    boolean insert_addition(String name, int num, double cost, String description, int weight) throws SQLException;
    boolean insert_dish(String name, int type_id, double cost, int size, int calories, Object[][] stucture) throws SQLException;
    boolean insert_sklad(String cell, String name, int num, Date date, int shelf_life) throws SQLException;
    boolean insert_sklad(String cell, String name, int num, int shelf_life) throws SQLException;
    boolean insert_type_dish(String name, Time[] feed_time) throws SQLException;
    boolean update_number_cell(String  cell_curr, int number_new) throws SQLException;
    boolean update_status_user(int id, String status) throws SQLException;
    boolean update_status_zakaz(int id, String status) throws SQLException;
}
