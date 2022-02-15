package javax.microedition.lcdui;

import things.CanvasPanel;

public abstract class Displayable {
    private String title;
    private Ticker ticker;

    public String getTitle() {
        return title;
    }

    public void setTitle(String s) {
        title = s;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public boolean isShown() {
        return true;
    }

    public void addCommand(Command cmd) { }

    public void removeCommand(Command cmd) { }

    public void setCommandListener(CommandListener l) { }

    public int getWidth() {
        return CanvasPanel.getInstance().getWidth();
    }

    public int getHeight() {
        return CanvasPanel.getInstance().getHeight();
    }

    protected void sizeChanged(int w, int h) { }
}
