package javax.microedition.lcdui;

public class Ticker {
    private String str;

    public Ticker(String str) {
        this.str = str;
    }

    public void setString(String str) {
        this.str = str;
    }

    public String getString() {
        return str;
    }
}