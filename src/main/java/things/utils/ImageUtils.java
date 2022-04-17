package things.utils;

import javax.microedition.lcdui.Graphics;

public class ImageUtils {
    public static int AlignX(int imageWidth, int x, int anchor) {
        int alignedX = x;
        if ((anchor & Graphics.RIGHT) != 0) {
            alignedX -= imageWidth;
        }
        else if ((anchor & Graphics.HCENTER) != 0) {
            alignedX -= imageWidth / 2;
        }
        return alignedX;
    }

    public static int AlignY(int imageHeight, int y, int anchor) {
        int alignedY = y;
        if ((anchor & Graphics.BOTTOM) != 0) {
            alignedY -= imageHeight;
        }
        else if ((anchor & Graphics.VCENTER) != 0) {
            alignedY -= imageHeight / 2;
        }
        return alignedY;
    }
}