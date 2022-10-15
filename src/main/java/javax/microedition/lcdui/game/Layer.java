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
    protected Dimension size = new Dimension(0, 0);
    protected Point position = new Point(0, 0);
    protected boolean isVisible = false;

    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }

    public void move(int dx, int dy) {
        position.setLocation(position.x + dx, position.y + dy);
    }

    public final int getX() {
        return position.x;
    }

    public final int getY() {
        return position.y;
    }

    public final int getWidth() {
        return size.width;
    }

    public final int getHeight() {
        return size.height;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public final boolean isVisible() {
        return isVisible;
    }

    public abstract void paint(Graphics g) throws NullPointerException;
}