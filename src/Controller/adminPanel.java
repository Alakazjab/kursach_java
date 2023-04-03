package Controller;

import Model.DbCon;
import Model.digitFilter;
import Model.tableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;

public class adminPanel extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable additionTable;
    private JTable dishTable;
    private JTable skladTable;
    private JTable typeDishTable;
    private JTable userTable;
    private JButton addAdditionButton;
    private JButton deleteAdditionButton;
    private JButton addDishButton;
    private JButton deleteDishButton;
    private JButton addSkladButton;
    private JButton DeleteSkladButton;
    private JButton addTypeDishButton;
    private JTable bedcellsTable;
    private JTable zakazTable;
    private JButton deleteZakazButton;
    private JTable compositionZakazTable;
    private JTextField additionNane;
    private JComboBox<String> additionOnSklad;
    private JTextField additionCost;
    private JTextPane descriptionField;
    private JTextField additionWeight;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTable CtructureTable;
    private JComboBox comboBox2;
    private JButton addStructureTable;
    private JTextField skladCell;
    private JTextField skladName;
    private JSpinner skladShelfLife;
    private JSpinner skladCount;
    private JTextField nameType_dish;
    private JTextField timeStartType_dish;
    private JTextField timeEndType_dish;
    private JCheckBox skladNowCheckBox;
    private JFormattedTextField skladDate;

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
        additionTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        zakazTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==2;
            }
        });
        dishTable.setModel(new DefaultTableModel() {
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
                return false;
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
        createTable(bedcellsTable, "Просроченные ячейки", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Просроченные ячейки\";")));
        createTable(typeDishTable, "Тип блюда", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Тип блюда\";")));
        createTable(zakazTable, "Заказ", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Заказ\";")));

        // Определение редактора ячеек для колонки
        additionTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(getListValues(typeDishTable,1))));
        dishTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(getListValues(skladTable,0))));
        zakazTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(new String[]{"готовится", "обрабатывается", "готов к выдаче"})));
        userTable.getColumnModel().getColumn(4)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(new String[]{"user", "admin"})));

        for (int i = 0; i < getListValues(skladTable,0).length; i++)
            additionOnSklad.addItem(getListValues(skladTable,0)[i]);
        PlainDocument doc = (PlainDocument) additionCost.getDocument();
        doc.setDocumentFilter(new digitFilter());
        doc = (PlainDocument) additionWeight.getDocument();
        doc.setDocumentFilter(new digitFilter());
        skladDate = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.SHORT));

        skladDate.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_MINUS)))
                {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid");
                    e.consume();
                }
            }
        });
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
        skladTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel)e.getSource();
                Object data = model.getValueAt(row, column);
                // Check if the changed cell is the redacted cell
                if (!data.equals((Object) "")) {
                    try {
                        new DbCon().update_number_cell((String) skladTable.getValueAt(row,1), Integer.parseInt((String) data));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        addAdditionButton.addActionListener(e -> {
            try {
                new DbCon().insert_addition(additionNane.getText(), Integer.parseInt((String) additionOnSklad.getSelectedItem()), Double.parseDouble(additionCost.getText()), descriptionField.getText(), Integer.parseInt(additionWeight.getText()));
                additionTable.setModel(new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                });
                createTable(additionTable, "Дополнение", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Дополнение\";")));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        addTypeDishButton.addActionListener(e -> {
            try {
                new DbCon().insert_type_dish(nameType_dish.getText(), Time.valueOf(timeStartType_dish.getText()), Time.valueOf(timeEndType_dish.getText()));
                typeDishTable.setModel(new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }});
                createTable(typeDishTable, "Тип блюда", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Тип блюда\";")));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteZakazButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (zakazTable.getSelectedRow() != -1)
                    {
                        new DbCon().delete_zakaz(Integer.parseInt((String)zakazTable.getValueAt(zakazTable.getSelectedRow(), 0)));
                        zakazTable.setModel(new DefaultTableModel() {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return column==2;
                            }
                        });
                        createTable(zakazTable, "Заказ", getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Заказ\";")));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addSkladButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
