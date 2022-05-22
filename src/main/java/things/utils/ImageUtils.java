package things.utils;

import javax.microedition.lcdui.Graphics;
import java.awt.image.BufferedImage;

public final class ImageUtils {
    public static int alignX(int imageWidth, int x, int anchor) {
        int alignedX = x;
        if ((anchor & Graphics.RIGHT) != 0) {
            alignedX -= imageWidth;
        }
        else if ((anchor & Graphics.HCENTER) != 0) {
            alignedX -= imageWidth >> 1;
        }
        return alignedX;
    }

    public static int alignY(int imageHeight, int y, int anchor) {
        int alignedY = y;
        if ((anchor & Graphics.BOTTOM) != 0) {
            alignedY -= imageHeight;
        }
        else if ((anchor & Graphics.VCENTER) != 0) {
            alignedY -= imageHeight >> 1;
        }
        return alignedY;
    }

    public static BufferedImage rotateImage(BufferedImage originalImage, int width, int height, double theta) {
        var rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        var graphics2D = rotatedImage.createGraphics();

        graphics2D.translate((height - width) >> 1, (height - width) >> 1);
        graphics2D.rotate(theta, height >> 1, width >> 1);
        graphics2D.drawRenderedImage(originalImage, null);

        return rotatedImage;
    }
}