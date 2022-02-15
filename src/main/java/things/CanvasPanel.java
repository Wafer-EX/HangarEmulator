package things;

import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {
    private static CanvasPanel instance;
    private static javax.microedition.lcdui.Canvas canvas;
    private static Graphics graphics;

    private CanvasPanel() { }

    public static CanvasPanel getInstance() {
        if (instance == null) {
            instance = new CanvasPanel();
        }
        return instance;
    }

    public static javax.microedition.lcdui.Canvas getCanvas() {
        return canvas;
    }

    public static void setCanvas(javax.microedition.lcdui.Canvas canvas) {
        CanvasPanel.canvas = canvas;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(240, 320);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (canvas != null) {
            CanvasPanel.graphics = graphics;
            canvas.paint(new javax.microedition.lcdui.Graphics(graphics));
        }
    }

    @Override
    public void repaint() {
        if (canvas != null && graphics != null) {
            canvas.paint(new javax.microedition.lcdui.Graphics(graphics));
        }
        super.repaint();
    }
}