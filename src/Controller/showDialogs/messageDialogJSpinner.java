package Controller.showDialogs;

import Controller.mainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class messageDialogJSpinner extends JFrame{
    private JSpinner spinner1;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel panel;
    private static int result;
    private static Object[] selectRow;

    public static void setSelectRow(Object[] selectRow) {
        messageDialogJSpinner.selectRow =  selectRow;
    }
    public static Object[] getSelectRow() {
        return selectRow;
    }
    public static int getResult() {
        return result;
    }
    public void setResult(int result) {
        messageDialogJSpinner.result = result;
    }
    public messageDialogJSpinner() {
    this.getContentPane().add(panel);

    addButton.addActionListener(e -> {
        setResult((Integer) spinner1.getValue());
        messageDialogJSpinner.setSelectRow(new Object[]{
                mainWindow.getSelectRow()[0],
                mainWindow.getSelectRow()[1],
                messageDialogJSpinner.getResult()
        });
        this.setVisible(false);
    });

    cancelButton.addActionListener(e -> {
        this.setVisible(false);
    });
}
}
