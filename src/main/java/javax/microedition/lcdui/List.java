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

public class List extends Screen implements Choice {
    public static final Command SELECT_COMMAND = null;

    public List(String title, int listType) { }

    public List(String title, int listType, String[] stringElements, Image[] imageElements) { }

    public void setTicker(Ticker ticker) { }

    public void setTitle(String s) { }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String getString(int elementNum) {
        return null;
    }

    @Override
    public Image getImage(int elementNum) {
        return null;
    }

    @Override
    public int append(String stringPart, Image imagePart) {
        return 0;
    }

    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) { }

    @Override
    public void delete(int elementNum) { }

    @Override
    public void deleteAll() { }

    @Override
    public void set(int elementNum, String stringPart, Image imagePart) { }

    @Override
    public boolean isSelected(int elementNum) {
        return false;
    }

    @Override
    public int getSelectedIndex() {
        return 0;
    }

    @Override
    public int getSelectedFlags(boolean[] selectedArray_return) {
        return 0;
    }

    @Override
    public void setSelectedIndex(int elementNum, boolean selected) { }

    @Override
    public void setSelectedFlags(boolean[] selectedArray) { }

    public void removeCommand(Command cmd) { }

    public void setSelectCommand(Command command) { }

    @Override
    public void setFitPolicy(int fitPolicy) { }

    @Override
    public int getFitPolicy() {
        return 0;
    }

    @Override
    public void setFont(int elementNum, Font font) { }

    @Override
    public Font getFont(int elementNum) {
        return null;
    }
}