package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class createTable {
    public static void addData(JTable table, String view, Object[][] data) throws SQLException {
        DbCon dbCon = new DbCon();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ResultSet resultSet = dbCon.getResultSet("select * from kursach.\"" + view + "\";");
        model.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        for (Object[] datum : data) model.addRow(datum);
    }
    public static void addDataAtQuery(JTable table, String query, Object[][] data) throws SQLException {
        DbCon dbCon = new DbCon();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ResultSet resultSet = dbCon.getResultSet(query);
        model.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        for (Object[] datum : data) model.addRow(datum);
    }
    public static void addDataAtListCoolumn(JTable table, Vector<String> columns, Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);
        for (Object[] datum : data) model.addRow(datum);
    }
    public static void addDataAtListCoolumn(JTable table, Vector<String> columns) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columns);
    }
    public static void addDataAtListCoolumn(JTable table, Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Object[] datum : data) model.addRow(datum);
    }
    public static Object[][] getDataFromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.last();
        //Retrieving the ResultSetMetaData object
        ResultSetMetaData rsmd = resultSet.getMetaData();
        //getting the column type
        int column_count = rsmd.getColumnCount();
        Object[][] result = new String[resultSet.getRow()][column_count];
        resultSet.beforeFirst();
        while (resultSet.next()) {
            for (int i = 0; i<column_count;i++) {
                result[resultSet.getRow()-1][i] = resultSet.getString(i+1);
            }
        }
        resultSet.close();
        return result;
    }
    public static String[] getListValues(JTable table, int column) {
        String[] result = new String[table.getRowCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            result[i] = (String) table.getValueAt(i,column);
        }
        return result;
    }
}
