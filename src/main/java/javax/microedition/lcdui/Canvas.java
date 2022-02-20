package javax.microedition.lcdui;

import things.CanvasPanel;

import java.awt.*;

public abstract class Canvas extends Displayable {
    public static final int UP = 1;
    public static final int DOWN = 6;
    public static final int LEFT = 2;
    public static final int RIGHT = 5;
    public static final int FIRE = 8;
    public static final int GAME_A = 9;
    public static final int GAME_B = 10;
    public static final int GAME_С = 11;
    public static final int GAME_D = 12;
    public static final int KEY_NUM0 = 48;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;
    public static final int KEY_STAR = 42;
    public static final int KEY_POUND = 35;

    protected Canvas() {
        CanvasPanel.setDisplayable(this);
    }

    public boolean hasPointerEvents(){
        return false;
    }

    public boolean hasPointerMotionEvents() {
        return false;
    }

    public boolean hasRepeatEvents() {
        return false;
    }

    public int getKeyCode(int gameAction) {
        // TODO: write method logic
        return gameAction;
    }

    public String getKeyName(int keyCode) {
        // TODO: write method logic
        return null;
    }

    public int getGameAction(int keyCode) {
        return keyCode;
    }

    public void setFullScreenMode(boolean mode) { }

    public abstract void keyPressed(int keyCode);

    public abstract void keyReleased(int keyCode);

    public void repaint() {
        CanvasPanel.getInstance().repaint();
    }

    public void serviceRepaints() {
        // TODO: write method logic
    }

    protected void showNotify() {
        // TODO: write method logic
    }

    protected void hideNotify() {
        // TODO: write method logic
    }

    public abstract void paint(Graphics graphics);

    protected void sizeChanged(int w, int h) { }
}