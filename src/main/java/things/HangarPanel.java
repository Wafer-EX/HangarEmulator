package things;

import javax.microedition.lcdui.Displayable;
import javax.swing.*;
import java.awt.*;

public class HangarPanel extends JPanel {
    private static HangarPanel instance;
    private static Displayable displayable;

    private HangarPanel() {
        setPreferredSize(HangarState.getResolution());
    }

    public static HangarPanel getInstance() {
        if (instance == null) {
            instance = new HangarPanel();
        }
        return instance;
    }

    public static Displayable getDisplayable() {
        return displayable;
    }

    public static void setDisplayable(Displayable displayable) {
        HangarPanel.displayable = displayable;
        if (getDisplayable() instanceof javax.microedition.lcdui.Canvas) {
            var canvas = (javax.microedition.lcdui.Canvas) displayable;
            canvas.showNotify();
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (displayable != null) {
            if (displayable instanceof javax.microedition.lcdui.Canvas canvas) {
                if (canvas.getWidth() != this.getWidth() || canvas.getHeight() != this.getHeight()) {
                    HangarState.setResolution(getSize());
                    canvas.sizeChanged(this.getWidth(), this.getHeight());
                }
                canvas.paint(new javax.microedition.lcdui.Graphics(graphics));
            }
        }
    }
}