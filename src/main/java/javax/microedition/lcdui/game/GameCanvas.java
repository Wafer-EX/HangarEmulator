package javax.microedition.lcdui.game;

import things.HangarPanel;
import things.HangarState;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameCanvas extends Canvas {
    public static final int UP_PRESSED = 1 << Canvas.UP;
    public static final int DOWN_PRESSED = 1 << Canvas.DOWN;
    public static final int LEFT_PRESSED = 1 << Canvas.LEFT;
    public static final int RIGHT_PRESSED = 1 << Canvas.RIGHT;
    public static final int FIRE_PRESSED = 1 << Canvas.FIRE;
    public static final int GAME_A_PRESSED = 1 << Canvas.GAME_A;
    public static final int GAME_B_PRESSED = 1 << Canvas.GAME_B;
    public static final int GAME_C_PRESSED = 1 << Canvas.GAME_C;
    public static final int GAME_D_PRESSED = 1 << Canvas.GAME_D;

    private Image image;

    protected GameCanvas(boolean suppressKeyEvents) {
        super();
        int width = HangarState.getResolution().width;
        int height = HangarState.getResolution().height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    protected Graphics getGraphics() {
        return new Graphics(image.getGraphics());
    }

    @Override
    public void paint(Graphics g) {
        if (g == null) {
            throw new NullPointerException();
        }
        HangarPanel.getInstance().getGraphics().drawImage(image, 0, 0, null);
    }
}