package Controller.interfaces;

import java.sql.Date;
import java.sql.Time;

public interface callProcedures {
    boolean add_user(String name, String email, String password, int age);
    boolean create_zakaz(int id_user, boolean dilivery, int[][] dish_number, int[][] addition_number);
    boolean create_zakaz(int id_user, boolean dilivery, int[][] dish_number, String type);
    boolean delete_zakaz(int zakaz_id);
    boolean insert_addition(String name, int num, double cost, String description, int weight);
    boolean insert_dish(String name, int type_id, double cost, int size, int calories, int[][] stucture);
    boolean insert_sklad(String cell, String name, int num, Date date, int shelf_life);
    boolean insert_sklad(String cell, String name, int num, int shelf_life);
    boolean insert_type_dish(String name, Time[] feed_time);
    boolean update_number_cell(String  cell_curr, int number_new);
    boolean update_status_user(int id, String status);
    boolean update_status_zakaz(int id, String status);
}
