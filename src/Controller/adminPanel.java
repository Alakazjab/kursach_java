package Controller;

import Model.DbCon;
import Model.tableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.sun.java.accessibility.util.SwingEventMonitor.addListSelectionListener;

public class adminPanel extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable dishTable;
    private JTable additionTable;
    private JTable skladTable;
    private JTable typeDishTable;
    private JTable userTable;
    private JButton добавитьButton;
    private JButton удалитьButton;
    private JButton добавитьButton2;
    private JButton удалитьButton1;
    private JButton добавитьButton1;
    private JButton удалитьButton2;
    private JButton добавитьButton3;
    private JButton удалитьButton3;
    private JButton добавитьButton4;
    private JButton удалитьButton4;
    private JTable bedcellsTable;
    private JButton button1;
    private JButton button2;
    private JTable zakazTable;
    private JButton добавитьButton5;
    private JButton удалитьButton5;
    private JTable compositionZakazTable;

    private void createTable(JTable table, String view, Object[][] data) throws SQLException {
        DbCon dbCon = new DbCon();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ResultSet resultSet = dbCon.getResultSet("select * from kursach.\"" + view + "\";");
        model.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        for (Object[] datum : data) model.addRow(datum);
    }
    private Object[][] getDataFromResultSet(ResultSet resultSet) throws SQLException {
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
    private String[] getListValues(JTable table, int column) {
        String[] result = new String[table.getRowCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            result[i] = (String) table.getValueAt(i,column);
        }
        return result;
    }
    private void updateDish(JTable table, int idRow) {

    }
    public adminPanel() throws HeadlessException, SQLException {
        this.getContentPane().add(panel1);

        //Запрет на редактирование Номера
        dishTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column!=0;
            }
        });
        zakazTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==2;
            }
        });
        additionTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column!=0;
            }
        });
        skladTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column!=0;
            }
        });
        userTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==4;
            }
        });
        typeDishTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column!=0;
            }
        });
        bedcellsTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        compositionZakazTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        // Заполнение таблиц данными
        createTable(dishTable, "Блюдо", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Блюдо\";")));
        createTable(additionTable, "Дополнение", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Дополнение\";")));
        createTable(skladTable, "Склад", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Склад\";")));
        createTable(userTable, "Пользователь", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Пользователь\";")));
        createTable(bedcellsTable, "Блюдо", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Блюдо\";")));
        createTable(typeDishTable, "Тип блюда", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Тип блюда\";")));
        createTable(zakazTable, "Заказ", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Заказ\";")));

        // Определение редактора ячеек для колонки
        dishTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(getListValues(typeDishTable,1))));
        additionTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(getListValues(skladTable,0))));
        zakazTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(new String[]{"готовится", "обрабатывается", "готов к выдаче"})));
        userTable.getColumnModel().getColumn(4)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(new String[]{"user", "admin"})));

        zakazTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) return;
                try {
                    DefaultTableModel model = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    compositionZakazTable.setModel(model);
                    model.setRowCount(0);
                    int row = zakazTable.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        createTable(compositionZakazTable, "Содержание заказа", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Содержание заказа\" where \"Номер заказа\" = "+zakazTable.getValueAt(row,0)+";")));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        zakazTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel)e.getSource();
                Object data = model.getValueAt(row, column);
                // Check if the changed cell is the redacted cell
                if (!data.equals((Object) "")) {
                    try {
                       new DbCon().update_status_zakaz(Integer.parseInt((String) zakazTable.getValueAt(row,0)),(String) data);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        userTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel)e.getSource();
                Object data = model.getValueAt(row, column);
                // Check if the changed cell is the redacted cell
                if (!data.equals((Object) "")) {
                    try {
                        new DbCon().update_status_user(Integer.parseInt((String) userTable.getValueAt(row,0)),(String) data);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}
