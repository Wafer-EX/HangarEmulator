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
import javax.microedition.lcdui.Image;

public class TiledLayer extends Layer {
    public TiledLayer(int columns, int rows, Image image, int tileWidth, int tileHeight) throws NullPointerException, IllegalArgumentException {
        // TODO: write constructor logic
    }

    public int createAnimatedTile(int staticTileIndex) throws IndexOutOfBoundsException {
        // TODO: write method logic
        return 0;
    }

    public void setAnimatedTile(int animatedTileIndex, int staticTileIndex) throws IndexOutOfBoundsException {
        // TODO: write method logic
    }

    public int getAnimatedTile(int animatedTileIndex) throws IndexOutOfBoundsException {
        // TODO: write method logic
        return 0;
    }

    public void setCell(int col, int row, int tileIndex) throws IndexOutOfBoundsException {
        // TODO: write method logic
    }

    public int getCell(int col, int row) throws IndexOutOfBoundsException {
        // TODO: write method logic
        return 0;
    }

    public void fillCells(int col, int row, int numCols, int numRows, int tileIndex) throws IndexOutOfBoundsException, IllegalArgumentException, IndexOutOfBoundsException {
        // TODO: write method logic
    }

    public final int getCellWidth() {
        // TODO: write method logic
        return 0;
    }

    public final int getCellHeight() {
        // TODO: write method logic
        return 0;
    }

    public final int getColumns() {
        // TODO: write method logic
        return 0;
    }

    public final int getRows() {
        // TODO: write method logic
        return 0;
    }

    public void setStaticTileSet(Image image, int tileWidth, int tileHeight) throws NullPointerException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void paint(Graphics g) throws NullPointerException {
        // TODO: write method logic
    }
}