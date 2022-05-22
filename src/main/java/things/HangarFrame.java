package things;

import javax.swing.*;
import java.awt.*;

public class HangarFrame extends JFrame {
    private static HangarFrame instance;
    private JLabel currentLabel;

    private HangarFrame() {
        this.setTitle("Hangar Emulator");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());
    }

    public static HangarFrame getInstance() {
        if (instance == null) {
            instance = new HangarFrame();
        }
        return instance;
    }

    public JLabel getLabel() {
        return currentLabel;
    }

    public void setLabel(JLabel label) {
        currentLabel = label;
        currentLabel.setPreferredSize(new Dimension(240, 320));
        currentLabel.setHorizontalAlignment(JLabel.CENTER);
        currentLabel.setVerticalAlignment(JLabel.CENTER);

        this.add(currentLabel);
        this.pack();
        this.revalidate();
    }

    public void setHangarPanel() {
        if (getLabel() != null) {
            this.remove(getLabel());
        }
        this.setJMenuBar(new HangarMenuBar());
        this.add(HangarPanel.getInstance());
        this.addKeyListener(new HangarKeyListener());
        this.pack();
        this.revalidate();
    }
}