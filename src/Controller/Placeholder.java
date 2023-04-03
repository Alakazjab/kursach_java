package Controller;

import javax.swing.*;
import java.awt.*;

public class Placeholder extends JFrame {
    public static void passwordFocusGained(JPasswordField passwordField, String placeholder) {
        if (!passwordField.getText().equals(placeholder)) return;
        passwordField.setForeground(Color.BLACK);
        passwordField.setText(null);
        passwordField.setEchoChar('•');
    }
    public static void textFocusGained(JTextField textField, String placeholder) {
        if (!textField.getText().equals(placeholder)) return;
        textField.setForeground(Color.BLACK);
        textField.setText(null);
    }
    public static void passwordFocusLost(JPasswordField passwordField, String placeholder) {
        if (!passwordField.getText().isEmpty()) return;
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char)0);
        passwordField.setText(placeholder);
    }
    public static void textFocusLost(JTextField textField, String placeholder) {
        if (!textField.getText().isEmpty()) return;
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
    }
}
