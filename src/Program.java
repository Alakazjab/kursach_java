import Model.authForm;

import java.awt.*;

public class Program {
    public static void main(String[] args) {
        authForm authForm = new authForm();
        authForm.pack();
        authForm.setSize(new Dimension(300, 300));
        authForm.setVisible(true);
    }
}
