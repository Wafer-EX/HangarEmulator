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

import things.HangarState;

import javax.microedition.lcdui.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LayerManager {
    private final ArrayList<Layer> layerList = new ArrayList<>();

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
        if (layerList.size() > 0) {
            var resolution = HangarState.getProfile().getResolution();
            var bufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_RGB);

            for (int i = layerList.size() - 1; i >= 0; i--) {
                var layer = layerList.get(i);
                layer.paint(new Graphics(bufferedImage.getGraphics()));
            }

            g.getSEGraphics().drawImage(bufferedImage, 0, 0, null);
        }
    }

    public void setViewWindow(int x, int y, int width, int height) throws IllegalArgumentException {
        // TODO: write method logic
    }
}