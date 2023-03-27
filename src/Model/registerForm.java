package Model;

import Controller.DbCon;
import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Objects;

import static javax.swing.JOptionPane.showMessageDialog;

public class registerForm extends JFrame {
    private JLabel authLebel;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JButton registerButton;
    private JPanel panel;
    private JLabel emailLabel;
    private JPasswordField confirmPassword;
    private JTextField nameField;
    private JTextField ageField;
    private JLabel ageLabel;
    private JLabel nameLabel;

    public registerForm() {
        this.getContentPane().add(panel);
        emailField.setForeground(Color.GRAY);
        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("E-mail")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
                emailLabel.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setForeground(Color.GRAY);
                    emailField.setText("E-mail");
                }
                try {
                    if (emailField.getText().isEmpty()) return;
                    if (!new DbCon().testUserLogin(emailField.getText())) {
                        emailLabel.setText("Такой email уже существует");
                        registerButton.setEnabled(false);
                    } else {
                        registerButton.setEnabled(true);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        registerButton.addActionListener(e -> {
            DbCon dbCon = new DbCon();
            if(emailField.getText().isEmpty() || nameField.getText().isEmpty() || passwordField1.getText().isEmpty() || ageField.getText().isEmpty()){
                showMessageDialog(null, "Все поля должны быть заполнены", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(!Objects.equals(passwordField1.getText(), confirmPassword.getText())){
                showMessageDialog(null, "Пароли не совпадают", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (new DbCon().add_user(nameField.getText(), emailField.getText(),
                    BCrypt.withDefaults().hashToString(12, (passwordField1.getPassword())), Integer.parseInt(ageField.getText()))){
                showMessageDialog(null, "Вы успешно зарегистрировались", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
    }
}
