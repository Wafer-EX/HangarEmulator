package javax.microedition.midlet;

import things.HangarPanel;

public abstract class MIDlet {
    private boolean blockExit;

    protected MIDlet() {
        blockExit = false;
    }

    public abstract void startApp() throws MIDletStateChangeException;

    public abstract void pauseApp();

    public abstract void destroyApp(boolean unconditional) throws MIDletStateChangeException;

    public void notifyDestroyed() {
        if (!blockExit) {
            System.exit(0);
        }
    }

    public final void notifyPaused() {
        HangarPanel.getInstance().repaint();
    }

    public final String getAppProperty(String key) {
        return System.getProperty(key);
    }

    public void setExitBlock(boolean blockExit) {
        this.blockExit = blockExit;
    }
}