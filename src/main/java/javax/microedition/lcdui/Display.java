package javax.microedition.lcdui;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import things.CanvasPanel;

import javax.microedition.midlet.MIDlet;

public class Display {
    public static final int LIST_ELEMENT = 1;
    public static final int CHOICE_GROUP_ELEMENT = 2;
    public static final int ALERT = 3;
    public static final int COLOR_BACKGROUND = 0;
    public static final int COLOR_FOREGROUND = 1;
    public static final int COLOR_HIGHLIGHTED_BACKGROUND = 2;
    public static final int COLOR_HIGHLIGHTED_FOREGROUND = 3;
    public static final int COLOR_BORDER = 4;
    public static final int COLOR_HIGHLIGHTED_BORDER = 5;

    public static Display getDisplay(MIDlet m) {
        return new Display();
    }

    public int getColor(int colorSpecifier) {
        // TODO: take it from config?
        return 0x00858585;
    }

    public int getBorderStyle(boolean highlighted) {
        // TODO: take it from config?
        return Graphics.SOLID;
    }

    public boolean isColor() {
        return true;
    }

    public int numColors() {
        // 8 bit display
        return 256;
    }

    public int numAlphaLevels() throws NotImplementedException {
        // TODO: how to do it?
        throw new NotImplementedException("numAlphaLevels");
    }

    public Displayable getCurrent() {
        return CanvasPanel.getDisplayable();
    }

    public void setCurrent(Displayable displayable) {
        CanvasPanel.setDisplayable(displayable);
    }

    public void setCurrent(Alert alert, Displayable nextDisplayable) {
        // TODO: use Alert
        setCurrent(nextDisplayable);
    }

    public void setCurrentItem(Item item) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("setCurrentItem");
    }

    public void callSerially(Runnable r) throws NotImplementedException {
        // TODO: write method logic
        //throw new NotImplementedException("callSerially");
    }

    public boolean flashBacklight(int duration) {
        if (duration < 0) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public boolean vibrate(int duration) {
        if (duration < 0) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public int getBestImageWidth(int imageType) {
        // TODO: how to do it?
        return 0;
    }

    public int getBestImageHeight(int imageType) {
        // TODO: how to do it?
        return 0;
    }
}