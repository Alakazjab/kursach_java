package Controller;

import Model.DbCon;
import Model.tableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class mainWindow extends JFrame {
    private JPanel panel1;
    private JTable menuTable;
    private JTabbedPane tabbedPane1;
    private JTable userZakazTable;

    public mainWindow() throws HeadlessException, SQLException  {
        this.getContentPane().add(panel1);
        DbCon dbCon = new DbCon();
        TableModel ptm = null;
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        JTable jTable = new JTable(ptm);
        new tableModel().addData(dbCon);
        String[] colName = new String[new tableModel().getColumnCount()];
        ResultSet resultSet = dbCon.getResultSet("select * from kursach.\"Блюдо\"");
        for(int i = 0; i < colName.length; i++) {
            model.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        }
        while (resultSet.next()){
            Vector<String> row = new Vector<String>();
            row.add(String.valueOf(resultSet.getInt(1)));
            row.add(resultSet.getString(2));
            row.add(resultSet.getString(3));
            row.add(String.valueOf(resultSet.getDouble(4)));
            row.add(String.valueOf(resultSet.getInt(5)));
            row.add(String.valueOf(resultSet.getInt(6)));
            model.addRow(row);
        }
    }
}
