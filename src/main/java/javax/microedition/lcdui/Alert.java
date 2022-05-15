package javax.microedition.lcdui;

import javax.swing.*;

public class Alert extends Screen {
    public Alert(String title, String alertText, Image alertImage, AlertType alertType){
        JOptionPane.showMessageDialog(null, alertText, title, JOptionPane.WARNING_MESSAGE);
    }

    public void setTimeout(int time) {
        // TODO: write method logic
    }
}