package Controller;

import Model.DbCon;
import Model.digitFilter;
import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.swing.*;
import javax.swing.text.PlainDocument;
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

        PlainDocument doc = (PlainDocument) ageField.getDocument();
        doc.setDocumentFilter(new digitFilter());

        emailField.setForeground(Color.GRAY);
        emailField.setText("E-mail");
        passwordField1.setForeground(Color.GRAY);
        confirmPassword.setForeground(Color.GRAY);
        passwordField1.setEchoChar((char)0);
        confirmPassword.setEchoChar((char)0);
        passwordField1.setText("Password");
        confirmPassword.setText("Confirm password");

        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               Placeholder.textFocusGained(emailField,"E-mail");
               if (! emailField.getText().isEmpty()) emailLabel.setText(null);
            }
            @Override
            public void focusLost(FocusEvent e) {
                Placeholder.textFocusLost(emailField,"E-mail");
                try {
                    if (emailField.getText().isEmpty()) return;
                    if (!new DbCon().testUserLogin(emailField.getText())) {
                        emailLabel.setText("Такой email уже существует");
                        registerButton.setEnabled(false);
                        return;
                    }
                    registerButton.setEnabled(true);
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        passwordField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Placeholder.passwordFocusGained(passwordField1, "Password");
            }
            @Override
            public void focusLost(FocusEvent e) {
                Placeholder.passwordFocusLost(passwordField1, "Password");
            }
        });
        confirmPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Placeholder.passwordFocusGained(confirmPassword, "Confirm password");
            }
            @Override
            public void focusLost(FocusEvent e) {
                Placeholder.passwordFocusLost(confirmPassword, "Confirm password");
            }
        });

        registerButton.addActionListener(e -> {
            if(emailField.getText().isEmpty() || nameField.getText().isEmpty() || passwordField1.getText().isEmpty() || ageField.getText().isEmpty()){
                showMessageDialog(null, "Все поля должны быть заполнены", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!Objects.equals(passwordField1.getText(), confirmPassword.getText())){
                showMessageDialog(null, "Пароли не совпадают", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                if (new DbCon().add_user(nameField.getText(), emailField.getText(),
                        BCrypt.withDefaults().hashToString(12, (passwordField1.getPassword())), Integer.parseInt(ageField.getText()))){
                    showMessageDialog(null, "Вы успешно зарегистрировались", "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                    authForm.setUser_id(new DbCon().return_user_id(emailField.getText()));
                    mainWindow mainWindow = new mainWindow();
                    mainWindow.pack();
                    mainWindow.setSize(new Dimension(900, 900));
                    mainWindow.setVisible(true);
                    dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
}
