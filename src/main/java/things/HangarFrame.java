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

    public void setLabel(HangarLabel label) {
        label.setPreferredSize(new Dimension(240, 320));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        this.add(label);
        this.pack();
        this.revalidate();
        currentLabel = label;
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