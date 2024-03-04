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

import java.awt.*;
import java.util.ArrayList;

public abstract class Item {
    public static final int LAYOUT_DEFAULT = 0;
    public static final int LAYOUT_LEFT = 1;
    public static final int LAYOUT_RIGHT = 2;
    public static final int LAYOUT_CENTER = 3;
    public static final int LAYOUT_TOP = 0x10;
    public static final int LAYOUT_BOTTOM = 0x20;
    public static final int LAYOUT_VCENTER = 0x30;
    public static final int LAYOUT_NEWLINE_BEFORE = 0x100;
    public static final int LAYOUT_NEWLINE_AFTER = 0x200;
    public static final int LAYOUT_SHRINK = 0x400;
    public static final int LAYOUT_EXPAND = 0x800;
    public static final int LAYOUT_VSHRINK = 0x1000;
    public static final int LAYOUT_VEXPAND = 0x2000;
    public static final int LAYOUT_2 = 0x4000;
    public static final int PLAIN = 0;
    public static final int HYPERLINK = 1;
    public static final int BUTTON = 2;

    private String label;
    protected int layout = LAYOUT_DEFAULT;
    private final ArrayList<Command> commands = new ArrayList<>();
    private ItemCommandListener itemCommandListener = null;
    private final Dimension preferredSize = new Dimension(-1, -1);
    private Command defaultCommand = null;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public void removeCommand(Command cmd) {
        commands.remove(cmd);
    }

    public void setItemCommandListener(ItemCommandListener l) {
        this.itemCommandListener = l;
    }

    public int getPreferredWidth() {
        return preferredSize.width;
    }

    public int getPreferredHeight() {
        return preferredSize.height;
    }

    public void setPreferredSize(int width, int height) {
        preferredSize.setSize(width, height);
    }

    public int getMinimumWidth() {
        // TODO: write method logic
        return 0;
    }

    public int getMinimumHeight() {
        // TODO: write method logic
        return 0;
    }

    public void setDefaultCommand(Command cmd) {
        this.defaultCommand = cmd;
    }

    public void notifyStateChanged() {
        // TODO: write method logic
    }
}