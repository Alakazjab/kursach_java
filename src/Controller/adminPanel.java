package Controller;

import Model.DbCon;
import Model.tableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class adminPanel extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable dishTable;
    private JTable additionTable;
    private JTable skladTable;
    private JTable typeDishTable;
    private JTable userTable;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JTable bedcellsTable;

    private void createTable(JTable table, String view, Object[][] data) throws SQLException {
        DbCon dbCon = new DbCon();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ResultSet resultSet = dbCon.getResultSet("select * from \"" + view + "\";");
        model.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        for (Object[] datum : data) model.addRow(datum);
    }
    private Object[][] getDataFromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.last();
        //Retrieving the ResultSetMetaData object
        ResultSetMetaData rsmd = resultSet.getMetaData();
        //getting the column type
        int column_count = rsmd.getColumnCount();
        resultSet.beforeFirst();
        Object[][] result = new String[resultSet.getRow()][column_count];
        while (resultSet.next()) {
            for (int i = 1; i<=column_count;i++) {
                result[resultSet.getRow()][i] = resultSet.getString(i);
            }
        }
        return result;
    }
    public adminPanel() throws HeadlessException, SQLException {
        this.getContentPane().add(panel1);
        DbCon dbCon = new DbCon();

        ResultSet resultSet = dbCon.getResultSet("select * from kursach.\"Блюдо\";");
        resultSet.next();
        System.out.println(resultSet.getString(1));
    }
}
