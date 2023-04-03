package Controller;

import Model.DbCon;
import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Objects;

public class authForm extends JFrame {
    private JLabel authLebel;
    private JPanel panel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton sign_inButton;
    private JButton registerButton;
    private JLabel loginField;
    private JLabel passwordLabel;
    private static int user_id;

    public static void setUser_id(int user_id) {
        authForm.user_id = user_id;
    }

    public static int getUser_id() {
        return user_id;
    }

    public authForm() {
        this.getContentPane().add(panel);

        textField1.setForeground(Color.GRAY);
        textField1.setText("E-mail");
        passwordField1.setForeground(Color.GRAY);
        passwordField1.setEchoChar((char)0);
        passwordField1.setText("Password");
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Placeholder.textFocusGained(textField1,"E-mail");
                loginField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) loginField.setText("Это поле необходимо Заполнить");
                Placeholder.textFocusLost(textField1, "E-mail");
            }
        });
        passwordField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Placeholder.passwordFocusGained(passwordField1, "Password");
                passwordLabel.setText(null);
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField1.getText().isEmpty()) passwordLabel.setText("Это поле необходимо Заполнить");
                Placeholder.passwordFocusLost(passwordField1, "Password");
            }
        });

        sign_inButton.addActionListener(e -> {
            try {
                if (!BCrypt.verifyer().verify(passwordField1.getPassword() , new DbCon().auth_user(textField1.getText()).toCharArray()).verified) {return;}
                try {
                    setUser_id(new DbCon().return_user_id(textField1.getText()));
                    if (Objects.equals(new DbCon().return_user_status(textField1.getText()), "admin")){
                        adminPanel adminPanel = new adminPanel();
                        adminPanel.pack();
                        adminPanel.setSize(new Dimension(500, 500));
                        adminPanel.setVisible(true);
                        dispose();
                        return;
                    }
                    mainWindow mainWindow = new mainWindow();
                    mainWindow.pack();
                    mainWindow.setSize(new Dimension(1000,500));
                    mainWindow.setVisible(true);
                    dispose();
                }

                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        registerButton.addActionListener(e -> {
            registerForm registerForm = new registerForm();
            registerForm.pack();
            registerForm.setSize(new Dimension(400, 400));
            registerForm.setVisible(true);
            dispose();
        });
    }
}
