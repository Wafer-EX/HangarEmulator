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

public class StringItem extends Item {
    private final int appearanceMode;
    private String text;
    private Font font;

    public StringItem(String label, String text) {
        this(label, text, PLAIN);
    }

    public StringItem(String label, String text, int appearanceMode) {
        this.setLabel(label);
        this.setText(text);
        this.appearanceMode = appearanceMode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAppearanceMode() {
        return appearanceMode;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font != null ? font : Font.getDefaultFont();
    }

    @Override
    public void setPreferredSize(int width, int height) {
        // TODO: write method logic
    }
}