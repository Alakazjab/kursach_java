package Controller.interfaces;

import java.sql.ResultSet;

public interface selectFunctions {
    ResultSet return_dishes_on_type_dish(int type_id);
    int[][] return_dishes_structure(int type_id);
    int[][] return_zakaz_addition_composition(int zakaz_id);
    int[][] return_zakaz_dish_composition(int zakaz_id);
    ResultSet return_zakaz_user(int user_id);

}
