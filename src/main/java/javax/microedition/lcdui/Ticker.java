package javax.microedition.lcdui;

public class Ticker {
    private String str;

    public Ticker(String str) throws NullPointerException {
        if (str == null) {
            throw new NullPointerException();
        }
        this.str = str;
    }

    public void setString(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.str = str;
    }

    public String getString() {
        return str;
    }
}