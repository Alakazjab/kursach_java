package Model;

import Controller.DbCon;
import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.swing.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class authForm extends JFrame {
    private JLabel authLebel;
    private JPanel panel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton войтиButton;
    private JButton registerButton;
    private JLabel loginField;

    public authForm() {
        this.getContentPane().add(panel);


        registerButton.addActionListener(e -> {
            DbCon dbCon = new DbCon();
            if(textField1.getText().length() < 3){
                showMessageDialog(null, "Логин слишком мал", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(textField1.getText().length() == 0){
                showMessageDialog(null, "Вы не ввели логин", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(passwordField1.getText().length() == 0){
                showMessageDialog(null, "Вы не ввели пароль", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (new DbCon().createUser(textField1.getText(),
                    BCrypt.withDefaults().hashToString(12, (passwordField1.getPassword())))){
                showMessageDialog(null, "Вы успешно зарегистрировались", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                hide();
            }
        });

        textField1.setForeground(Color.GRAY);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals("Login")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
                loginField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.GRAY);
                    textField1.setText("Login");
                }
                try {
                    if (textField1.getText().isEmpty()) return;
                    if (!new DbCon().testUserLogin(textField1.getText())) {
                        loginField.setText("Такой логин уже существует");
                        registerButton.setEnabled(false);
                    } else {
                        registerButton.setEnabled(true);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        войтиButton.addActionListener(e -> {

        });
        registerButton.addActionListener(e -> {

        });
    }
}
