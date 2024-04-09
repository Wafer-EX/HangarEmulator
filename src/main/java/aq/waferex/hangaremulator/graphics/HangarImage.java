package aq.waferex.hangaremulator.graphics;

public abstract class HangarImage {
    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize);
}