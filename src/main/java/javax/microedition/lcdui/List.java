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

import things.implementations.additions.ListElement;

import java.util.ArrayList;

public class List extends Screen implements Choice {
    public static final Command SELECT_COMMAND = new Command("", Command.SCREEN, 0);

    private final ArrayList<ListElement> elements = new ArrayList();
    private int listType;
    private Ticker ticker;

    public List(String title, int listType) {
        setTitle(title);
        this.listType = listType;
    }

    public List(String title, int listType, String[] stringElements, Image[] imageElements) {
        this(title, listType);
    }

    @Override
    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public String getString(int elementNum) {
        return elements.get(elementNum).getString();
    }

    @Override
    public Image getImage(int elementNum) {
        return elements.get(elementNum).getImage();
    }

    @Override
    public int append(String stringPart, Image imagePart) {
        elements.add(new ListElement(stringPart, imagePart, false, Font.getDefaultFont()));
        return elements.size();
    }

    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) {
        // TODO: write method logic
    }

    @Override
    public void delete(int elementNum) {
        elements.remove(elementNum);
    }

    @Override
    public void deleteAll() {
        elements.clear();
    }

    @Override
    public void set(int elementNum, String stringPart, Image imagePart) {
        var element = elements.get(elementNum);
        element.setString(stringPart);
        element.setImage(imagePart);
    }

    @Override
    public boolean isSelected(int elementNum) {
        return elements.get(elementNum).getSelected();
    }

    @Override
    public int getSelectedIndex() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public int getSelectedFlags(boolean[] selectedArray_return) {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void setSelectedIndex(int elementNum, boolean selected) {
        // TODO: write method logic
    }

    @Override
    public void setSelectedFlags(boolean[] selectedArray) {
        // TODO: write method logic
    }

    @Override
    public void removeCommand(Command cmd) {
        // TODO: write method logic
    }

    public void setSelectCommand(Command command) {
        // TODO: write method logic
    }

    @Override
    public void setFitPolicy(int fitPolicy) {
        // TODO: write method logic
    }

    @Override
    public int getFitPolicy() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void setFont(int elementNum, Font font) {
        elements.get(elementNum).setFont(font);
    }

    @Override
    public Font getFont(int elementNum) {
        return elements.get(elementNum).getFont();
    }
}