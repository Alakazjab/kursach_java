package Controller;


import javax.swing.*;
import java.awt.*;

public class NumericCellEditor extends DefaultCellEditor {

    public NumericCellEditor() {
        super(new JTextField());
    }

    @Override
    public boolean stopCellEditing() {
        try {
            Integer.parseInt((String) getCellEditorValue());
            return super.stopCellEditing();
        } catch (NumberFormatException e) {
            ((JComponent) getComponent()).setBorder(
                    BorderFactory.createLineBorder(Color.red));
            return false;
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value, boolean isSelected, int row, int column) {
        ((JTextField) getComponent()).setText(String.valueOf(value));
        return super.getTableCellEditorComponent(table, value, isSelected,
                row, column);
    }
}