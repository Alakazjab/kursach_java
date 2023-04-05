package Controller;

import Model.DbCon;
import Model.createTable;
import Model.digitFilter;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

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
    private JComboBox<String> typeDishList;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTable structureTable;
    private JComboBox<String> skladIdList;
    private JButton addStructureTable;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTextField nameType_dish;
    private JFormattedTextField timeStartType_dish;
    private JFormattedTextField timeEndType_dish;
    private JCheckBox nowCheckBox;
    private JSpinner spinner3;
    private JSpinner spinner4;
    private JSpinner spinner5;
    private JSpinner spinner6;
    private JSpinner spinner7;
    private JSpinner spinner8;
    private final String TITLE_confirm = "Подтверждение";


    public adminPanel() throws HeadlessException, SQLException {
        this.getContentPane().add(panel1);

        //Запрет на редактирование Номера
        additionTable.setModel(new DefaultTableModel() {
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
        structureTable.setModel(new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return Integer.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        });
        // Заполнение таблиц данными
        createTable.addData(additionTable, "Дополнение", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Дополнение\";")));
        createTable.addData(dishTable, "Блюдо", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Блюдо\";")));
        createTable.addData(skladTable, "Склад", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Склад\";")));
        createTable.addData(userTable, "Пользователь", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Пользователь\";")));
        createTable.addData(bedcellsTable, "Блюдо", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Блюдо\";")));
        createTable.addData(typeDishTable, "Тип блюда", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Тип блюда\";")));
        createTable.addData(zakazTable, "Заказ", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Заказ\";")));
        createTable.addDataAtListCoolumn(structureTable, new Vector<String>(Arrays.asList("Номер", "Количество")));

        // Определение редактора ячеек для колонки
        dishTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(createTable.getListValues(typeDishTable,1))));
        dishTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(createTable.getListValues(skladTable,0))));
        additionTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(createTable.getListValues(skladTable,0))));
        zakazTable.getColumnModel().getColumn(2)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(new String[]{"готовится", "обрабатывается", "готов к выдаче"})));
        userTable.getColumnModel().getColumn(4)
                .setCellEditor(new DefaultCellEditor(new JComboBox<String>(new String[]{"user", "admin"})));
        structureTable.getColumnModel().getColumn(1).setCellEditor(new NumericCellEditor());
        additionTable.getColumnModel().getColumn(5).setCellEditor(new NumericCellEditor());
        dishTable.getColumnModel().getColumn(4).setCellEditor(new NumericCellEditor());
        dishTable.getColumnModel().getColumn(5).setCellEditor(new NumericCellEditor());
        skladTable.getColumnModel().getColumn(3).setCellEditor(new NumericCellEditor());
        skladTable.getColumnModel().getColumn(5).setCellEditor(new NumericCellEditor());
        spinner1.setModel(new SpinnerNumberModel(1, 0, 1000, 1));
        spinner2.setModel(new SpinnerNumberModel(1, 0, 1000, 1));
        spinner3.setModel(new SpinnerNumberModel(1, 0, 24, 1));
        spinner4.setModel(new SpinnerNumberModel(1, 0, 59, 1));
        spinner5.setModel(new SpinnerNumberModel(1, 0, 59, 1));
        spinner6.setModel(new SpinnerNumberModel(1, 0, 24, 1));
        spinner7.setModel(new SpinnerNumberModel(1, 0, 59, 1));
        spinner8.setModel(new SpinnerNumberModel(1, 0, 59, 1));

        JFormattedTextField field1 = ((JSpinner.NumberEditor)
                spinner1.getEditor()).getTextField();
        ((NumberFormatter) field1.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter1 = (DefaultFormatter) field1.getFormatter();
        formatter1.setCommitsOnValidEdit(true);

        JFormattedTextField field2 = ((JSpinner.NumberEditor)
                spinner2.getEditor()).getTextField();
        ((NumberFormatter) field2.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter2 = (DefaultFormatter) field2.getFormatter();
        formatter2.setCommitsOnValidEdit(true);

        JFormattedTextField field3 = ((JSpinner.NumberEditor)
                spinner2.getEditor()).getTextField();
        ((NumberFormatter) field3.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter3 = (DefaultFormatter) field3.getFormatter();
        formatter3.setCommitsOnValidEdit(true);

        JFormattedTextField field4 = ((JSpinner.NumberEditor)
                spinner2.getEditor()).getTextField();
        ((NumberFormatter) field4.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter4 = (DefaultFormatter) field4.getFormatter();
        formatter4.setCommitsOnValidEdit(true);

        JFormattedTextField field5 = ((JSpinner.NumberEditor)
                spinner2.getEditor()).getTextField();
        ((NumberFormatter) field5.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter5 = (DefaultFormatter) field5.getFormatter();
        formatter5.setCommitsOnValidEdit(true);

        JFormattedTextField field6 = ((JSpinner.NumberEditor)
                spinner2.getEditor()).getTextField();
        ((NumberFormatter) field6.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter6 = (DefaultFormatter) field6.getFormatter();
        formatter6.setCommitsOnValidEdit(true);

        JFormattedTextField field7 = ((JSpinner.NumberEditor)
                spinner2.getEditor()).getTextField();
        ((NumberFormatter) field7.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter7 = (DefaultFormatter) field7.getFormatter();
        formatter7.setCommitsOnValidEdit(true);

        JFormattedTextField field8 = ((JSpinner.NumberEditor)
                spinner2.getEditor()).getTextField();
        ((NumberFormatter) field8.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter8 = (DefaultFormatter) field8.getFormatter();
        formatter8.setCommitsOnValidEdit(true);

        // Заполнение нетабличных полей фиксированными значениями
        for (int i = 0; i < createTable.getListValues(skladTable,0).length; i++) {
            additionOnSklad.addItem(createTable.getListValues(skladTable, 0)[i]);
            skladIdList.addItem(createTable.getListValues(skladTable, 0)[i]);
        }
        for (int i = 0; i < createTable.getListValues(typeDishTable,0).length; i++) {
            typeDishList.addItem(createTable.getListValues(typeDishTable, 0)[i]);
        }
        PlainDocument doc = (PlainDocument) additionCost.getDocument();
        doc.setDocumentFilter(new digitFilter());
        doc = (PlainDocument) additionWeight.getDocument();
        doc.setDocumentFilter(new digitFilter());
        Format shortTime = DateFormat.getTimeInstance(DateFormat.SHORT);
        timeStartType_dish = new JFormattedTextField(shortTime);
        timeStartType_dish.setValue(new Date());
        timeEndType_dish = new JFormattedTextField(shortTime);
        timeEndType_dish.setValue(new Date());

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
                        createTable.addData(compositionZakazTable, "Содержание заказа", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Содержание заказа\" where \"Номер заказа\" = "+zakazTable.getValueAt(row,0)+";")));
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
                if (column!=2) return;
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
                DefaultTableModel model = (DefaultTableModel) additionTable.getModel();
                model.setRowCount(0);
                createTable.addData(additionTable, "Блюдо", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Блюдо\";")));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        addTypeDishButton.addActionListener(e -> {
            try {
                new DbCon().insert_type_dish(nameType_dish.getText(), Time.valueOf(spinner6.getValue() + ":" + spinner7.getValue() + ":" + spinner8.getValue()), Time.valueOf(spinner3.getValue() + ":" + spinner4.getValue() + ":" + spinner5.getValue()));
                DefaultTableModel model = (DefaultTableModel) typeDishTable.getModel();
                model.setRowCount(0);
                createTable.addData(typeDishTable, "Тип блюда", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Тип блюда\";")));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteZakazButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (zakazTable.getSelectedRow() != -1 && JOptionPane.showConfirmDialog(null,
                            "Вы не отказываетесь?",
                            TITLE_confirm,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
                    {
                        new DbCon().delete_zakaz(Integer.parseInt((String) zakazTable.getValueAt(zakazTable.getSelectedRow(), 0)));
                        DefaultTableModel model = (DefaultTableModel) zakazTable.getModel();
                        model.setRowCount(0);
                        createTable.addData(zakazTable, "Заказ", createTable.getDataFromResultSet(new DbCon().getResultSet("select * from kursach.\"Заказ\";")));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addStructureTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable.addDataAtListCoolumn(structureTable, new Object[][]{{skladIdList.getSelectedItem(),0}});
            }
        });
        deleteAdditionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
