package things.implementations.additions;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

public class ListElement {
    private String string;
    private Image image;
    private boolean isSelected;
    private Font font;

    public ListElement(String string, Image image, boolean isSelected, Font font) {
        this.string = string;
        this.image = image;
        this.isSelected = isSelected;
        this.font = font;
    }

    public String getString() {
        return string;
    }

    public void setString(String title) {
        this.string = title;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}