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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TiledLayer extends Layer {
    private final int rows;
    private final int columns;
    private final Dimension tileSize = new Dimension();
    private final int[][] cells;
    private final ArrayList<java.awt.Image> tilesList = new ArrayList<>();

    public TiledLayer(int columns, int rows, Image image, int tileWidth, int tileHeight) throws NullPointerException, IllegalArgumentException {
        this.columns = columns;
        this.rows = rows;
        this.tileSize.setSize(tileWidth, tileHeight);
        this.size.setSize(image.getWidth(), image.getHeight());
        this.cells = new int[columns][rows];

        for (int y = 0; y < image.getHeight() / tileHeight; y++) {
            for (int x = 0; x < image.getWidth() / tileWidth; x++) {
                var bufferedImage = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
                var subImage = image.getSEImage().getSubimage(tileWidth * x, tileHeight * y, tileWidth, tileHeight);

                bufferedImage.getGraphics().drawImage(subImage, 0, 0, null);
                tilesList.add(bufferedImage);
            }
        }
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
        cells[col][row] = tileIndex;
    }

    public int getCell(int col, int row) throws IndexOutOfBoundsException {
        return cells[col][row];
    }

    public void fillCells(int col, int row, int numCols, int numRows, int tileIndex) throws IndexOutOfBoundsException, IllegalArgumentException, IndexOutOfBoundsException {
        for (int y = row; y < row + numRows; y++) {
            for (int x = col; x < col + numCols; x++) {
                cells[x][y] = tileIndex;
            }
        }
    }

    public final int getCellWidth() {
        return tileSize.width;
    }

    public final int getCellHeight() {
        return tileSize.height;
    }

    public final int getColumns() {
        return columns;
    }

    public final int getRows() {
        return rows;
    }

    public void setStaticTileSet(Image image, int tileWidth, int tileHeight) throws NullPointerException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void paint(Graphics g) throws NullPointerException {
        if (g == null) {
            throw new NullPointerException();
        }
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (cells[x][y] != 0) {
                    var tile = tilesList.get(cells[x][y] - 1);
                    int posX = position.x + tileSize.width * x;
                    int posY = position.y + tileSize.height * y;
                    g.getSEGraphics().drawImage(tile, posX, posY, null);
                }
            }
        }
    }
}