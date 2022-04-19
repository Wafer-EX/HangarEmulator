package javax.microedition.midlet;

public abstract class MIDlet {
    protected MIDlet() { }

    public abstract void startApp() throws MIDletStateChangeException;

    public abstract void pauseApp();

    public abstract void destroyApp(boolean unconditional) throws MIDletStateChangeException;

    public void notifyDestroyed() {
        System.exit(0);
    }

    public final String getAppProperty(String key) {
        return null;
    }
}