package View;

import javax.swing.*;
import java.awt.*;

public class LoginView {
    public LoginView() {

    }

    public String[] testBox() {
        //JOptionPane.showMessageDialog(null, "This is a dialog box", "title", JOptionPane.PLAIN_MESSAGE);
        JPanel loginCredentials = new JPanel(new GridLayout(2,2));
        loginCredentials.add(new JLabel("Username: "));
        JTextField username = new JTextField();
        loginCredentials.add(username);

        loginCredentials.add(new JLabel("Password: "));
        JTextField password = new JTextField();
        loginCredentials.add(password);

        int login = JOptionPane.showConfirmDialog(null, loginCredentials, "Input Dialog", JOptionPane.OK_CANCEL_OPTION);

        if (login == JOptionPane.OK_OPTION) {
            String usernameData = username.getText();
            String passwordData = password.getText();
            return new String[] {usernameData, passwordData};
        }
        else {
            return new String[] {"", ""};
        }


    }

}
