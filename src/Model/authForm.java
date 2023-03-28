package Model;

import Controller.DbCon;
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

    public authForm() {
        this.getContentPane().add(panel);

        textField1.setForeground(Color.GRAY);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals("E-mail")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
                loginField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.GRAY);
                    textField1.setText("E-mail");
                }
            }
        });
        sign_inButton.addActionListener(e -> {
            try {
                if (!BCrypt.verifyer().verify(passwordField1.getPassword() , new DbCon().auth_user(textField1.getText()).toCharArray()).verified) {return;}
                try {
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
                    mainWindow.setSize(new Dimension(500,500));
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
            registerForm.setSize(new Dimension(300, 300));
            registerForm.setVisible(true);
            dispose();
        });
    }
}
