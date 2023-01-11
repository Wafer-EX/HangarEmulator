/*
 * Copyright 2022-2023 Kirill Lomakin
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

public class ImageItem extends Item {
    public static final int LAYOUT_DEFAULT = 0;
    public static final int LAYOUT_LEFT = 1;
    public static final int LAYOUT_RIGHT = 2;
    public static final int LAYOUT_CENTER = 3;
    public static final int LAYOUT_NEWLINE_BEFORE = 0x100;
    public static final int LAYOUT_NEWLINE_AFTER = 0x200;

    private Image image;
    private String altText;
    private final int appearanceMode;

    public ImageItem(String label, Image img, int layout, String altText) {
        this(label, img, layout, altText, PLAIN);
    }

    public ImageItem(String label, Image image, int layout, String altText, int appearanceMode) {
        this.setLabel(label);
        this.setImage(image);
        this.setLayout(layout);
        this.setAltText(altText);
        this.appearanceMode = appearanceMode;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        this.image = img;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String text) {
        this.altText = text;
    }

    @Override
    public int getLayout() {
        return layout;
    }

    @Override
    public void setLayout(int layout) {
        this.layout = layout;
    }

    public int getAppearanceMode() {
        return appearanceMode;
    }
}