package things;

import javax.swing.*;
import java.awt.*;

public class HangarLabel extends JLabel {
    public HangarLabel(String text) {
        super(text);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        var resolution = HangarState.getResolution();
        if (resolution.width != getWidth() || resolution.height != getHeight()) {
            HangarState.setResolution(getSize());
        }
        super.paintComponent(graphics);
    }
}