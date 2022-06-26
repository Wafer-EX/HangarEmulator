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

package javax.microedition.lcdui;

import things.ui.HangarFrame;
import things.ui.components.HangarPanel;

import javax.microedition.midlet.MIDlet;

public class Display {
    public static final int LIST_ELEMENT = 1;
    public static final int CHOICE_GROUP_ELEMENT = 2;
    public static final int ALERT = 3;
    public static final int COLOR_BACKGROUND = 0;
    public static final int COLOR_FOREGROUND = 1;
    public static final int COLOR_HIGHLIGHTED_BACKGROUND = 2;
    public static final int COLOR_HIGHLIGHTED_FOREGROUND = 3;
    public static final int COLOR_BORDER = 4;
    public static final int COLOR_HIGHLIGHTED_BORDER = 5;

    private static Display display;
    private final HangarPanel hangarPanel;

    private Display(HangarPanel hangarPanel) {
        this.hangarPanel = hangarPanel;
    }

    public static Display getDisplay(MIDlet m) {
        if (display == null) {
            display = new Display(HangarFrame.getInstance().getHangarPanel());
        }
        return display;
    }

    public int getColor(int colorSpecifier) throws IllegalArgumentException {
        // TODO: choose colors
        return switch (colorSpecifier) {
            case COLOR_BACKGROUND -> 0;
            case COLOR_FOREGROUND -> 0;
            case COLOR_HIGHLIGHTED_BACKGROUND -> 0;
            case COLOR_HIGHLIGHTED_FOREGROUND -> 0;
            case COLOR_BORDER -> 0;
            case COLOR_HIGHLIGHTED_BORDER -> 0;
            default -> throw new IllegalArgumentException();
        };
    }

    public int getBorderStyle(boolean highlighted) {
        // TODO: check it
        return Graphics.SOLID;
    }

    public boolean isColor() {
        return true;
    }

    public int numColors() {
        // TODO: check it
        return 256;
    }

    public int numAlphaLevels() {
        // TODO: check it
        return 256;
    }

    public Displayable getCurrent() {
        return hangarPanel.getDisplayable();
    }

    public void setCurrent(Displayable displayable) {
        hangarPanel.setDisplayable(displayable);
    }

    public void setCurrent(Alert alert, Displayable nextDisplayable) throws NullPointerException, IllegalArgumentException {
        if (alert == null) {
            throw new NullPointerException();
        }
        if (nextDisplayable instanceof Alert) {
            throw new IllegalArgumentException();
        }
        // TODO: use Alert
        setCurrent(nextDisplayable);
    }

    public void setCurrentItem(Item item) throws IllegalStateException, NullPointerException {
        // TODO: write method logic
    }

    public void callSerially(Runnable r) {
        hangarPanel.setCallSerially(r);
    }

    public boolean flashBacklight(int duration) throws IllegalArgumentException {
        if (duration < 0) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public boolean vibrate(int duration) throws IllegalArgumentException {
        if (duration < 0) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public int getBestImageWidth(int imageType) throws IllegalArgumentException {
        // TODO: choose resolutions
        return switch (imageType) {
            case LIST_ELEMENT -> 0;
            case CHOICE_GROUP_ELEMENT -> 0;
            case ALERT -> 0;
            default -> throw new IllegalArgumentException();
        };
    }

    public int getBestImageHeight(int imageType) throws IllegalArgumentException {
        // TODO: choose resolutions
        return switch (imageType) {
            case LIST_ELEMENT -> 0;
            case CHOICE_GROUP_ELEMENT -> 0;
            case ALERT -> 0;
            default -> throw new IllegalArgumentException();
        };
    }
}