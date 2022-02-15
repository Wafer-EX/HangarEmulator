package javax.microedition.lcdui;

import things.CanvasPanel;

import javax.microedition.midlet.MIDlet;

public class Display {
    public static Display getDisplay(MIDlet midlet) {
        return new Display();
    }

    public void setCurrent(Displayable displayable){
        CanvasPanel.setCanvas((Canvas)displayable);
    }
}