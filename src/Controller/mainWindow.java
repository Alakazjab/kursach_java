package Controller;

import Controller.showDialogs.messegeDialodJSpinner;
import Model.DbCon;
import Model.tableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class mainWindow extends JFrame {
    private JPanel panel1;
    private JTable menuTable;
    private JTabbedPane tabbedPane1;
    private JTable userZakazTable;
    private JTable table1;
    private JTable table2;
    private JButton заказатьButton;
    private JCheckBox CheckBox1;
    private JTable additionTable;
    public List<String> selectedRow;
    public int[][] dishes;
    public int[][] additions;
    private int count;
    public mainWindow() throws HeadlessException, SQLException  {
        this.getContentPane().add(panel1);
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {

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
        resultSet.close();

    DefaultTableModel mod = new DefaultTableModel() {
        @Override
        public Class<?> getColumnClass(int column){
            return switch (column) {
                case 2 -> Boolean.class;
                default -> String.class;
            };
        }
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 2;
        }
    };
//        userZakazTable.setModel(mod);
//        DefaultTableModel modelz = (DefaultTableModel) userZakazTable.getModel();
//        resultSet = dbCon.return_zakaz_user(authForm.getUser_id());
//        Vector<String> vector = new Vector<String>(Arrays.asList("Номер", "Состояние", "Доставка", "Стоимость"));
//        modelz.setColumnIdentifiers(vector);
//        while (resultSet.next()){
//            List<String> row = Arrays.asList(resultSet.getString(1).replace('(',(char)0).replace(')',(char)0).split(","));
//            modelz.addRow(new Object[0]);
//            modelz.setValueAt(row.get(0),resultSet.getRow(),0);
//            modelz.setValueAt(row.get(1),resultSet.getRow(),1);
//            modelz.setValueAt(!Objects.equals(row.get(2), "f"),resultSet.getRow(),2);
//            modelz.setValueAt(row.get(3),resultSet.getRow(),3);
//        }
        menuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) return;//ето типо даблклик
                int row = table1.rowAtPoint(e.getPoint());
                if (row > -1) {
                    int realRow = table1.convertRowIndexToModel(row);
                    for(int i = 0; i < colName.length; i++) {

                    }
                }
                messegeDialodJSpinner messegeDialodJSpinner = new messegeDialodJSpinner();
                messegeDialodJSpinner.pack();
                messegeDialodJSpinner.setSize(new Dimension(200, 100));
                messegeDialodJSpinner.setVisible(true);
            }
        });
        заказатьButton.addActionListener(e -> {
            try {
                if (table1.getRowCount()>0 && table2.getRowCount()==0)
                    new DbCon().create_zakaz(authForm.getUser_id(), CheckBox1.isSelected(), new int[0][],"dish");
                if (table1.getRowCount()==0 && table2.getRowCount()>0)
                    new DbCon().create_zakaz(authForm.getUser_id(), CheckBox1.isSelected(), new int[0][],"addition");
                if (table1.getRowCount()>0 && table2.getRowCount()>0)
                    new DbCon().create_zakaz(authForm.getUser_id(), CheckBox1.isSelected(), new int[0][],new int[0][]);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        tabbedPane1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tabbedPane1.getSelectedIndex() == 2) {

                }
            }
        });
        tabbedPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tabbedPane1.getSelectedIndex() == 2) {
                    return;
                }
            }
        });
    }
}
