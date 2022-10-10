/*
 * Copyright 2022 Kirill Lomakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;

public class Sprite extends Layer {
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT90 = 5;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;

    private Image image;
    private int frameWidth;
    private int frameHeight;
    private Point referencePixel = new Point(0, 0);

    public Sprite(Image image) throws NullPointerException {
        this(image, image.getWidth(), image.getHeight());
    }

    public Sprite(Image image, int frameWidth, int frameHeight) throws NullPointerException, IllegalArgumentException {
        this.setImage(image, frameWidth, frameHeight);
    }

    public Sprite(Sprite s) throws NullPointerException {
        this(s.image, s.frameWidth, s.frameHeight);
    }

    public void defineReferencePixel(int x, int y) {
        referencePixel.setLocation(x, y);
    }

    public void setRefPixelPosition(int x, int y) {
        // TODO: write method logic
    }

    public int getRefPixelX() {
        // TODO: write method logic
        return 0;
    }

    public int getRefPixelY() {
        // TODO: write method logic
        return 0;
    }

    public void setFrame(int sequenceIndex) throws IndexOutOfBoundsException {
        // TODO: write method logic
    }

    public final int getFrame() {
        // TODO: write method logic
        return 0;
    }

    public int getRawFrameCount() {
        // TODO: write method logic
        return 0;
    }

    public int getFrameSequenceLength() {
        // TODO: write method logic
        return 0;
    }

    public void nextFrame() {
        // TODO: write method logic
    }

    public void prevFrame() {
        // TODO: write method logic
    }

    @Override
    public void paint(Graphics g) throws NullPointerException {
        // TODO: write method logic
    }

    public void setFrameSequence(int[] sequence) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO: write method logic
    }

    public void setImage(Image img, int frameWidth, int frameHeight) throws NullPointerException, IllegalArgumentException {
        if (img == null) {
            throw new NullPointerException();
        }
        this.image = img;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public void defineCollisionRectangle(int x, int y, int width, int height) throws IllegalArgumentException {
        // TODO: write method logic
    }

    public void setTransform(int transform) throws IllegalArgumentException {
        // TODO: write method logic
    }

    public final boolean collidesWith(Sprite s, boolean pixelLevel) throws NullPointerException {
        // TODO: write method logic
        return false;
    }

    //public final boolean collidesWith(TiledLayer t, boolean pixelLevel) {
        // TODO: write method logic
    //}

    public final boolean collidesWith(Image image, int x, int y, boolean pixelLevel) throws NullPointerException {
        // TODO: write method logic
        return false;
    }
}