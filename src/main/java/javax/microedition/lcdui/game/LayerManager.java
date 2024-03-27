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

import javax.microedition.lcdui.Graphics;
import java.awt.*;
import java.util.ArrayList;

public class LayerManager {
    private final ArrayList<Layer> layerList = new ArrayList<>();
    private final Point viewWindowPosition = new Point();
    private final Dimension viewWindowSize = new Dimension();

    public LayerManager() { }

    public void append(Layer l) throws NullPointerException {
        if (l == null) {
            throw new NullPointerException();
        }
        layerList.add(l);
    }

    public void insert(Layer l, int index) throws NullPointerException, IndexOutOfBoundsException {
        if (l == null) {
            throw new NullPointerException();
        }
        layerList.add(index, l);
    }

    public Layer getLayerAt(int index) throws IndexOutOfBoundsException {
        return layerList.get(index);
    }

    public int getSize() {
        return layerList.size();
    }

    public void remove(Layer l) throws NullPointerException {
        layerList.remove(l);
    }

    public void paint(Graphics g, int x, int y) throws NullPointerException {
        if (g == null) {
            throw new NullPointerException();
        }
        if (!layerList.isEmpty()) {
            int offsetX = x - viewWindowPosition.x;
            int offsetY = y - viewWindowPosition.y;

            for (int i = layerList.size() - 1; i >= 0; i--) {
                var layer = layerList.get(i);
                layer.move(offsetX, offsetY);
                layer.paint(g);
                layer.move(-offsetX, -offsetY);
            }
        }
    }

    public void setViewWindow(int x, int y, int width, int height) throws IllegalArgumentException {
        viewWindowPosition.setLocation(x, y);
        viewWindowSize.setSize(width, height);
    }
}