package Controller;

import javax.swing.*;
import javax.swing.text.*;

public class JTextFieldTemplateExample {
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("JTextField Template Example");
//        JPanel panel = new JPanel();
//        JTextField textField = new JTextField(20);
//        // Set the document filter to validate based on the template
//        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new TemplateDocumentFilter("###-###-####"));
//        panel.add(textField);
//        frame.add(panel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }
    // DocumentFilter that validates based on a template
    public static class TemplateDocumentFilter extends DocumentFilter {
        private String template;
        private char placeholder;
        public TemplateDocumentFilter(String template) {
            this.template = template;
            this.placeholder = '#';
        }
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder builder = new StringBuilder(template.substring(offset));
            for (int i = 0; i < string.length(); i++) {
                for (int j = 0; j < builder.length(); j++) {
                    if (builder.charAt(j) == placeholder) {
                        fb.insertString(offset + j, string.substring(i, i + 1), attr);
                        builder = builder.replace(j, j + 1, "");
                        break;
                    }
                }
            }
        }
        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            fb.remove(offset, length);
        }
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.isEmpty()) {
                fb.remove(offset, length);
            } else {
                insertString(fb, offset, text, attrs);
            }
        }
    }
}