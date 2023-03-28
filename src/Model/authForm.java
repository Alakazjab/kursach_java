package Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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

        });
        registerButton.addActionListener(e -> {
            registerForm registerForm = new registerForm();
            registerForm.pack();
            registerForm.setSize(new Dimension(300, 300));
            registerForm.setVisible(true);
        });
    }
}
