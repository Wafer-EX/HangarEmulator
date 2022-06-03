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
import java.awt.*;

public abstract class Layer {
    private Dimension position = new Dimension(0, 0);
    private boolean isVisible = false;

    public void setPosition(int x, int y) {
        position = new Dimension(x, y);
    }

    public void move(int dx, int dy) {
        position = new Dimension(position.width + dx, position.height + dy);
    }

    public final int getX() {
        return position.width;
    }

    public final int getY() {
        return position.height;
    }

    public final int getWidth() {
        // TODO: write method logic
        return 0;
    }

    public final int getHeight() {
        // TODO: write method logic
        return 0;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public final boolean isVisible() {
        return isVisible;
    }

    public abstract void paint(Graphics g);
}