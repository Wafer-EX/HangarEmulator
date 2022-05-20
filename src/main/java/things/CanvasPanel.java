package things;

import javax.microedition.lcdui.Displayable;
import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {
    private static CanvasPanel instance;
    private static Displayable displayable;

    private CanvasPanel() {
        setPreferredSize(new Dimension(240, 320));
    }

    public static CanvasPanel getInstance() {
        if (instance == null) {
            instance = new CanvasPanel();
        }
        return instance;
    }

    public static Displayable getDisplayable() {
        return displayable;
    }

    public static void setDisplayable(Displayable displayable) {
        if (displayable != null && displayable instanceof javax.microedition.lcdui.Canvas) {
            ((javax.microedition.lcdui.Canvas) displayable).hideNotify();
        }
        CanvasPanel.displayable = displayable;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (displayable != null) {
            if (displayable instanceof javax.microedition.lcdui.Canvas) {
                var canvas = (javax.microedition.lcdui.Canvas) displayable;
                if (canvas.getWidth() != getPreferredSize().width || canvas.getHeight() != getPreferredSize().height) {
                    canvas.sizeChanged(getPreferredSize().width, getPreferredSize().height);
                }
                canvas.showNotify();
                canvas.paint(new javax.microedition.lcdui.Graphics(graphics));
            }
        }
    }
}