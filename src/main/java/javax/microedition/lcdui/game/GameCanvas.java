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

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.graphics.HangarGraphicsProvider;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.image.BufferedImage;

public abstract class GameCanvas extends Canvas {
    public static final int UP_PRESSED = 1 << Canvas.UP;
    public static final int DOWN_PRESSED = 1 << Canvas.DOWN;
    public static final int LEFT_PRESSED = 1 << Canvas.LEFT;
    public static final int RIGHT_PRESSED = 1 << Canvas.RIGHT;
    public static final int FIRE_PRESSED = 1 << Canvas.FIRE;
    public static final int GAME_A_PRESSED = 1 << Canvas.GAME_A;
    public static final int GAME_B_PRESSED = 1 << Canvas.GAME_B;
    public static final int GAME_C_PRESSED = 1 << Canvas.GAME_C;
    public static final int GAME_D_PRESSED = 1 << Canvas.GAME_D;

    private BufferedImage additionalBuffer;

    protected GameCanvas(boolean suppressKeyEvents) {
        super();
        var graphicsSettings = HangarState.getGraphicsSettings();
        int width = graphicsSettings.getResolution().width;
        int height = graphicsSettings.getResolution().height;

        additionalBuffer = ImageUtils.createCompatibleImage(width, height);
    }

    protected Graphics getGraphics() {
        return new Graphics(new HangarGraphicsProvider(additionalBuffer.getGraphics()));
    }

    public int getKeyStates() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void paint(Graphics g) {
        // TODO: don't create image each time
        g.drawImage(new Image(additionalBuffer, false), 0, 0, 0);
    }

    public void flushGraphics(int x, int y, int width, int height) {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        canvasWrapper.getBufferedImage().getGraphics().drawImage(additionalBuffer, x, y, width, height, null);
        super.repaint(x, y, width, height);
    }

    public void flushGraphics() {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        canvasWrapper.getBufferedImage().getGraphics().drawImage(additionalBuffer, 0, 0, additionalBuffer.getWidth(), additionalBuffer.getHeight(), null);
        super.repaint();
    }

    @Override
    public void sizeChanged(int w, int h) {
        this.additionalBuffer = ImageUtils.createCompatibleImage(w, h);
    }
}