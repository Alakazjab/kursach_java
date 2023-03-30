package Model.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface selectFunctions {
    ResultSet return_dishes_on_type_dish(int type_id) throws SQLException;
    int[][] return_dishes_structure(int type_id) throws SQLException;
    int[][] return_zakaz_addition_composition(int zakaz_id) throws SQLException;
    int[][] return_zakaz_dish_composition(int zakaz_id) throws SQLException;
    ResultSet return_zakaz_user(int user_id) throws SQLException;
    String return_user_status(String email) throws SQLException;
    String auth_user(String email) throws SQLException;

}
