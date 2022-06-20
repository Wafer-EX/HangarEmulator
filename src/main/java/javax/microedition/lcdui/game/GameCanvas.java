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

import things.HangarPanel;
import things.HangarState;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import java.awt.*;
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

    private static final GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    private BufferedImage additionalBuffer;

    protected GameCanvas(boolean suppressKeyEvents) {
        super();
        int width = HangarState.getResolution().width;
        int height = HangarState.getResolution().height;
        additionalBuffer = graphicsConfiguration.createCompatibleImage(width, height);
    }

    protected Graphics getGraphics() {
        var graphics = additionalBuffer.getGraphics();
        HangarState.applyRenderingHints(graphics);
        return new Graphics(graphics);
    }

    @Override
    public void paint(Graphics g) {
        // TODO: write method logic?
    }

    public void flushGraphics(int x, int y, int width, int height) {
        var hangarPanel = HangarPanel.getInstance();
        hangarPanel.getBuffer().getGraphics().drawImage(additionalBuffer, x, y, width, height, null);
        hangarPanel.repaint();
        HangarState.syncWithFrameRate();
    }

    public void flushGraphics() {
        var hangarPanel = HangarPanel.getInstance();
        hangarPanel.getBuffer().getGraphics().drawImage(additionalBuffer, 0, 0, null);
        hangarPanel.repaint();
        HangarState.syncWithFrameRate();
    }

    @Override
    public void sizeChanged(int w, int h) {
        additionalBuffer = graphicsConfiguration.createCompatibleImage(w, h);
    }
}