/*
 * Copyright 2022-2024 Wafer EX
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

import aq.waferex.hangaremulator.graphics.HangarImage;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.util.ArrayList;

public class Sprite extends Layer {
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT90 = 5;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;

    private Image sprite;
    private int[] sequence;
    private int selectedIndex = 0;
    private final Point referencePixel = new Point();
    private final ArrayList<HangarImage> frameList = new ArrayList<>();
    private int transform = TRANS_NONE;

    public Sprite(Image image) throws NullPointerException {
        this(image, image.getWidth(), image.getHeight());
    }

    public Sprite(Image image, int frameWidth, int frameHeight) throws NullPointerException, IllegalArgumentException {
        this.setImage(image, frameWidth, frameHeight);
    }

    public Sprite(Sprite s) throws NullPointerException {
        this(s.sprite, s.size.width, s.size.height);
    }

    public void defineReferencePixel(int x, int y) {
        referencePixel.setLocation(x, y);
    }

    public void setRefPixelPosition(int x, int y) {
        // TODO: write method logic
    }

    public int getRefPixelX() {
        return referencePixel.x;
    }

    public int getRefPixelY() {
        return referencePixel.y;
    }

    public void setFrame(int sequenceIndex) throws IndexOutOfBoundsException {
        if (sequenceIndex < 0 || sequenceIndex >= sequence.length) {
            throw new IndexOutOfBoundsException();
        }
        this.selectedIndex = sequenceIndex;
    }

    public final int getFrame() {
        return selectedIndex;
    }

    public int getRawFrameCount() {
        return frameList.size();
    }

    public int getFrameSequenceLength() {
        if (sequence == null) {
            return frameList.size();
        }
        else {
            return sequence.length;
        }
    }

    public void nextFrame() {
        selectedIndex += 1;
        if (selectedIndex >= (sequence == null ? frameList.size() : sequence.length)) {
            selectedIndex = 0;
        }
    }

    public void prevFrame() {
        selectedIndex -= 1;
        if (selectedIndex < 0) {
            selectedIndex = sequence.length - 1;
        }
    }

    @Override
    public void paint(Graphics g) throws NullPointerException {
        var image = frameList.get(sequence == null ? selectedIndex : sequence[selectedIndex]);
        g.getGraphicsProvider().drawImage(image.getCopy(transform), position.x, position.y, false, false, 0.0f);
    }

    public void setFrameSequence(int[] sequence) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (sequence != null) {
            if (sequence.length < 1) {
                throw new IllegalArgumentException();
            }
            for (int frame : sequence) {
                if (frame < 0 || frame >= this.getRawFrameCount()) {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
        }
        this.selectedIndex = 0;
        this.sequence = sequence;
    }

    public void setImage(Image img, int frameWidth, int frameHeight) throws NullPointerException, IllegalArgumentException {
        if (img == null) {
            throw new NullPointerException();
        }
        this.sprite = img;
        this.size.width = frameWidth;
        this.size.height = frameHeight;

        frameList.clear();
        for (int y = 0; y < img.getHeight() / frameHeight; y++) {
            for (int x = 0; x < img.getWidth() / frameWidth; x++) {
                var image = img.getHangarImage().getCopy(frameWidth * x, frameHeight * y, frameWidth, frameHeight, 0);
                frameList.add(image);
            }
        }

        var sequence = new int[frameList.size()];
        for (int i = 0; i < frameList.size(); i++) {
            sequence[i] = i;
        }
        this.sequence = sequence;
    }

    public void defineCollisionRectangle(int x, int y, int width, int height) throws IllegalArgumentException {
        // TODO: write method logic
    }

    public void setTransform(int transform) throws IllegalArgumentException {
        this.transform = transform;
    }

    public final boolean collidesWith(Sprite s, boolean pixelLevel) throws NullPointerException {
        // TODO: write method logic
        return false;
    }

    public final boolean collidesWith(TiledLayer t, boolean pixelLevel) throws NullPointerException {
        // TODO: write method logic
        return false;
    }

    public final boolean collidesWith(Image image, int x, int y, boolean pixelLevel) throws NullPointerException {
        // TODO: write method logic
        return false;
    }
}