import things.*;

import javax.swing.*;

public class HangarEmulator {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();

        mainWindow.setTitle("Hangar Emulator");
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainWindow.setJMenuBar(new HangarMenuBar());
        mainWindow.add(HangarPanel.getInstance());
        mainWindow.addKeyListener(new HangarKeyListener());
        mainWindow.pack();

        mainWindow.setVisible(true);
    }
}