package things;

import javax.microedition.lcdui.Displayable;
import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {
    private static CanvasPanel instance;
    private static Displayable displayable;
    private static Graphics graphics;

    private CanvasPanel() { }

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
        CanvasPanel.displayable = displayable;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(240, 320);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (displayable != null) {
            CanvasPanel.graphics = graphics;
            if (displayable instanceof javax.microedition.lcdui.Canvas) {
                var canvas = (javax.microedition.lcdui.Canvas) displayable;
                canvas.paint(new javax.microedition.lcdui.Graphics(graphics));
            }
        }
    }

    @Override
    public void repaint() {
        if (displayable != null && graphics != null) {
            if (displayable instanceof javax.microedition.lcdui.Canvas) {
                var canvas = (javax.microedition.lcdui.Canvas) displayable;
                canvas.paint(new javax.microedition.lcdui.Graphics(graphics));
            }
        }
        super.repaint();
    }
}