package Controller.showDialogs;

import Controller.mainWindow;

import javax.swing.*;

public class messegeDialodJSpinner extends JFrame{
    private JSpinner spinner1;
    private JButton добавитьButton;
    private JButton отменаButton;
    private JPanel panel;
    private int rasult;

    public int getRasult() {
        return rasult;
    }

    public void setRasult(int rasult) {
        this.rasult = rasult;
    }

    public messegeDialodJSpinner() {
    this.getContentPane().add(panel);
    добавитьButton.addActionListener(e -> {
        setRasult((Integer) spinner1.getValue());
        this.setVisible(false);
    });
    отменаButton.addActionListener(e -> {
        this.setVisible(false);
    });
}
}
