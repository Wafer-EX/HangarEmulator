package javax.microedition.midlet;

public abstract  class MIDlet {
    public abstract void startApp();

    public abstract void pauseApp();

    public abstract void destroyApp(boolean unconditional);
}