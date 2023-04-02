package Controller.showDialogs;

import Controller.mainWindow;
import Model.digitFilter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;
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
        spinner1.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
        JFormattedTextField field1 = ((JSpinner.NumberEditor)
                spinner1.getEditor()).getTextField();
        ((NumberFormatter) field1.getFormatter()).setAllowsInvalid(false);
        DefaultFormatter formatter1 = (DefaultFormatter) field1.getFormatter();
        formatter1.setCommitsOnValidEdit(true);
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
