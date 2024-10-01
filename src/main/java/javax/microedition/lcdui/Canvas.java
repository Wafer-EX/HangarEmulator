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

package javax.microedition.lcdui;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.utils.microedition.CanvasUtils;
import aq.waferex.hangaremulator.utils.CanvasWrapperUtils;

public abstract class Canvas extends Displayable {
    public static final int UP = 1;
    public static final int DOWN = 6;
    public static final int LEFT = 2;
    public static final int RIGHT = 5;
    public static final int FIRE = 8;
    public static final int GAME_A = 9;
    public static final int GAME_B = 10;
    public static final int GAME_C = 11;
    public static final int GAME_D = 12;
    public static final int KEY_NUM0 = 48;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;
    public static final int KEY_STAR = 42;
    public static final int KEY_POUND = 35;

    protected Canvas() { }

    public boolean isDoubleBuffered() {
        // TODO: it is correct?
        return true;
    }

    public boolean hasPointerEvents() {
        return true;
    }

    public boolean hasPointerMotionEvents() {
        return true;
    }

    public boolean hasRepeatEvents() {
        return true;
    }

    public int getKeyCode(int gameAction) {
        return CanvasUtils.gameActionToKeyCode(gameAction);
    }

    public String getKeyName(int keyCode) {
        // TODO: write method logic
        return null;
    }

    public int getGameAction(int keyCode) {
        return CanvasUtils.keyCodeToGameAction(keyCode);
    }

    public void setFullScreenMode(boolean mode) { }

    public void keyPressed(int keyCode) { }

    public void keyRepeated(int keyCode) { }

    public void keyReleased(int keyCode) { }

    public void pointerPressed(int x, int y) { }

    public void pointerReleased(int x, int y) { }

    public void pointerDragged(int x, int y) { }

    // TODO: check it because I'm not sure is it work
    public final void repaint(int x, int y, int width, int height) {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        if (canvasWrapper != null) {
            var screenImage = HangarState.getScreenImage();
            int screenImageWidth = screenImage.getWidth();
            int screenImageHeight = screenImage.getHeight();

            float scaleFactor = CanvasWrapperUtils.getImageScaleFactor(screenImageWidth, screenImageHeight, canvasWrapper.getWidth(), canvasWrapper.getHeight());
            float imagePosX = canvasWrapper.getWidth() / 2.0f - (screenImageWidth * scaleFactor) / 2.0f;
            float imagePosY = canvasWrapper.getHeight() / 2.0f - (screenImageHeight * scaleFactor) / 2.0f;

            canvasWrapper.repaint(
                    (int) (imagePosX + x * scaleFactor),
                    (int) (imagePosY + y * scaleFactor),
                    (int) (width * scaleFactor),
                    (int) (height * scaleFactor)
            );
        }
        HangarState.syncWithFrameRate();
    }

    public final void repaint() {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        if (canvasWrapper != null) {
            canvasWrapper.repaint();
        }
        HangarState.syncWithFrameRate();
    }

    public final void serviceRepaints() {
        // TODO: write method logic?
    }

    public void showNotify() { }

    public void hideNotify() { }

    public abstract void paint(Graphics graphics);

    @Override
    public void sizeChanged(int w, int h) { }
}