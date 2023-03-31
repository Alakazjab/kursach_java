package Controller;

import Controller.showDialogs.messegeDialodJSpinner;
import Model.DbCon;
import Model.tableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class mainWindow extends JFrame {
    private JPanel panel1;
    private JTable menuTable;
    private JTabbedPane tabbedPane1;
    private JTable userZakazTable;
    private JSpinner количествоSpinner;
    private JButton добавитьButton;
    private JTable table1;
    private JButton заказатьButton;
    private JCheckBox доставкаCheckBox;
    private int count;
    public mainWindow() throws HeadlessException, SQLException  {
        this.getContentPane().add(panel1);
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        menuTable.setModel(tableModel);
        DbCon dbCon = new DbCon();
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
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

        userZakazTable.setModel(tableModel);
        DefaultTableModel modelz = (DefaultTableModel) userZakazTable.getModel();
        new tableModel().addData(dbCon);
        colName = new String[new tableModel().getColumnCount()];
        resultSet = dbCon.return_zakaz_user(authForm.getUser_id());
        for(int i = 0; i < colName.length; i++) {
            modelz.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        }
        while (resultSet.next()){
            Vector<String> row = new Vector<String>();
            row.add(String.valueOf(resultSet.getInt(1)));
            row.add(resultSet.getString(2));
            row.add(String.valueOf(resultSet.getBoolean(3)));
            row.add(String.valueOf(resultSet.getDouble(4)));
            model.addRow(row);
        }
        menuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) return;//ето типо даблклик
                messegeDialodJSpinner messegeDialodJSpinner = new messegeDialodJSpinner();
                messegeDialodJSpinner.pack();
                messegeDialodJSpinner.setSize(new Dimension(400, 400));
                messegeDialodJSpinner.setVisible(true);
            }
        });
        заказатьButton.addActionListener(e -> {

        });
    }
}
