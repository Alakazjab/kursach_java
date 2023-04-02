package Controller;

import Controller.showDialogs.messageDialogJSpinner;
import Model.DbCon;
import Model.tableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class mainWindow extends JFrame {
    private JPanel panel1;
    private JTable menuTable;
    private JTabbedPane tabbedPane1;
    private JTable userZakazTable;
    private JTable zakazDish;
    private JTable zakazAddition;
    private JButton createZakazButton;
    private JCheckBox diliveryCeckBox;
    private JTable additionTable;
    private JButton button1;
    public Object[][] dishes;
    public Object[][] additions;
    private static Object[] selectRow;
    private String typeBasket;

    public String getTypeBasket() {
        return typeBasket;
    }

    public void setTypeBasket(String typeBasket) {
        this.typeBasket = typeBasket;
    }

    public static Object[] getSelectRow() {
        return selectRow;
    }

    public static void setSelectRow(Object[] selectRow) {
        mainWindow.selectRow = selectRow;
    }
    public void addRowInKorzinaDish(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) zakazDish.getModel();
        model.addRow(row);
    }

    public mainWindow() throws HeadlessException, SQLException  {

        this.getContentPane().add(panel1);
        menuTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DbCon dbCon = new DbCon();
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        ResultSet resultSet = dbCon.getResultSet("select * from kursach.\"Блюдо\"");
        model.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        while (resultSet.next()){
            Vector<String> row = new Vector<>();
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
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
        additionTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DefaultTableModel modelAddition = (DefaultTableModel) additionTable.getModel();
        resultSet = dbCon.getResultSet("select \"Дополнение\".\"Номер\"," +
                "\"Дополнение\".\" Название\",\"Дополнение\".\"Цена\",\"Дополнение\".\"Описание\"," +
                "\"Дополнение\".\"Вес\" from kursach.\"Дополнение\"");
        modelAddition.setColumnIdentifiers(new tableModel().whatColNames(resultSet));
        while (resultSet.next()){
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(resultSet.getInt(1)));
            row.add(resultSet.getString(2));
            row.add(String.valueOf(resultSet.getDouble(3)));
            row.add(String.valueOf(resultSet.getString(4)));
            row.add(String.valueOf(resultSet.getInt(5)));
            modelAddition.addRow(row);
        }
        zakazDish.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        });
        DefaultTableModel zakazDishModel = (DefaultTableModel) zakazDish.getModel();
        zakazDishModel.setColumnIdentifiers(new Vector<>(Arrays.asList("Номер", "Название", "Количество")));

        zakazAddition.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        });
        DefaultTableModel zakazAdditionModel = (DefaultTableModel) zakazAddition.getModel();
        zakazAdditionModel.setColumnIdentifiers(new Vector<>(Arrays.asList("Номер", "Название", "Количество")));

        menuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) return;//ето типо даблклик
//                JOptionPane.show
                int row = menuTable.rowAtPoint(e.getPoint());
                if (row > -1) {
                    setTypeBasket("Dish");
                    int realRow = zakazDish.convertRowIndexToModel(row);
                    mainWindow.setSelectRow(new String[] {(String) menuTable.getValueAt(realRow, 0), (String) menuTable.getValueAt(realRow, 1)});
                    messageDialogJSpinner messageDialogSpinner = new messageDialogJSpinner();
                    messageDialogSpinner.pack();
                    messageDialogSpinner.setSize(new Dimension(300, 200));
                    messageDialogSpinner.setVisible(true);
                }
            }
        });
        additionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) return;//ето типо даблклик
                int row = additionTable.rowAtPoint(e.getPoint());
                if (row > -1) {
                    setTypeBasket("Addition");
                    int realRow = zakazDish.convertRowIndexToModel(row);
                    mainWindow.setSelectRow(new String[] {(String) additionTable.getValueAt(realRow, 0), (String) additionTable.getValueAt(realRow, 1)});
                    messageDialogJSpinner messageDialogSpinner = new messageDialogJSpinner();
                    messageDialogSpinner.pack();
                    messageDialogSpinner.setSize(new Dimension(300, 200));
                    messageDialogSpinner.setVisible(true);
                }
            }
        });
        createZakazButton.addActionListener(e -> {
            try {
                additions = new Object[zakazAddition.getRowCount()][2];
                for (int i = 0; i < zakazAddition.getRowCount(); i++) {
                    additions[i][0] = zakazAddition.getValueAt(i,0);
                    additions[i][1] = zakazAddition.getValueAt(i,2);
                }
                dishes = new Object[zakazDish.getRowCount()][2];
                for (int i = 0; i < zakazDish.getRowCount(); i++) {
                    dishes[i][0] = zakazDish.getValueAt(i,0);
                    dishes[i][1] = zakazDish.getValueAt(i,2);
                }
                if (zakazDish.getRowCount()>0 && zakazAddition.getRowCount()==0)
                    new DbCon().create_zakaz(authForm.getUser_id(), diliveryCeckBox.isSelected(), dishes,"dish");
                if (zakazDish.getRowCount()==0 && zakazAddition.getRowCount()>0)
                    new DbCon().create_zakaz(authForm.getUser_id(), diliveryCeckBox.isSelected(), additions,"addition");
                if (zakazDish.getRowCount()>0 && zakazAddition.getRowCount()>0)
                    new DbCon().create_zakaz(authForm.getUser_id(), diliveryCeckBox.isSelected(), dishes, additions);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        tabbedPane1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tabbedPane1.getSelectedIndex() == 2) {
                    if (!Arrays.equals(messageDialogJSpinner.getSelectRow(), null)){
                        if (Objects.equals(getTypeBasket(), "Dish"))
                            ((DefaultTableModel) zakazDish.getModel()).addRow(messageDialogJSpinner.getSelectRow());
                        if (Objects.equals(getTypeBasket(), "Addition"))
                            ((DefaultTableModel) zakazAddition.getModel()).addRow(messageDialogJSpinner.getSelectRow());
                        messageDialogJSpinner.setSelectRow(null);
                    }
                }
                if (tabbedPane1.getSelectedIndex() == 1) {
                    try {
                        userZakazTable.setModel(mod);
                        mod.setRowCount(0);
                        DefaultTableModel modelz = (DefaultTableModel) userZakazTable.getModel();
                        ResultSet resultSet = dbCon.return_zakaz_user(authForm.getUser_id());
                        Vector<String> vector = new Vector<String>(Arrays.asList("Номер", "Состояние", "Доставка", "Стоимость"));
                        modelz.setColumnIdentifiers(vector);
                        while (resultSet.next()){
                            Object[] row = Arrays.asList(resultSet.getString(1).replace('(', (char) 0).replace(')', (char) 0).split(",")).toArray();
                            modelz.addRow(row);
                        }
                    }
                    catch (RuntimeException | SQLException exception) {
                        throw new RuntimeException(exception);
                    }

                }
            }
        });
    }
}
