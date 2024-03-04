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

import things.HangarState;
import things.graphics.HangarOffscreenBuffer;
import things.graphics.gl.HangarGLOffscreenBuffer;
import things.graphics.swing.HangarSwingOffscreenBuffer;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

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

    private final HangarOffscreenBuffer offscreenBuffer;

    protected GameCanvas(boolean suppressKeyEvents) {
        super();
        var profile = HangarState.getProfileManager().getCurrentProfile();
        int width = profile.getResolution().width;
        int height = profile.getResolution().height;

        offscreenBuffer = switch (profile.getGraphicsEngine()) {
            case Swing -> new HangarSwingOffscreenBuffer(width, height);
            case OpenGL -> new HangarGLOffscreenBuffer(width, height);
        };
    }

    protected Graphics getGraphics() {
        // TODO: use anti-aliasing support
        return new Graphics(offscreenBuffer.getGraphicsProvider());
    }

    public int getKeyStates() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void paint(Graphics g) {
        g.getGraphicsProvider().paintOffscreenBuffer(offscreenBuffer);
    }

    public void flushGraphics(int x, int y, int width, int height) {
        offscreenBuffer.flushToCanvasWrapper(x, y, width, height);
        super.repaint(x, y, width, height);
    }

    public void flushGraphics() {
        offscreenBuffer.flushToCanvasWrapper(0, 0, offscreenBuffer.getWidth(), offscreenBuffer.getHeight());
        super.repaint();
    }

    @Override
    public void sizeChanged(int w, int h) {
        offscreenBuffer.refreshResolution(w, h);
    }
}