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

package javax.microedition.lcdui;

import aq.waferex.hangaremulator.ui.components.wrappers.HangarListWrapper;

import java.util.ArrayList;
import java.util.Arrays;

public class List extends Screen implements Choice {
    public static final Command SELECT_COMMAND = new Command("", Command.SCREEN, 0);

    private final ArrayList<String> strings = new ArrayList<>();
    private final int listType;
    private Ticker ticker;
    private Command selectCommand = SELECT_COMMAND;
    private int fitPolicy = TEXT_WRAP_DEFAULT;
    private int selectedElement = 0;

    // TODO: use it
    private HangarListWrapper listWrapper;

    public List(String title, int listType) {
        setTitle(title);
        this.listType = listType;
    }

    public List(String title, int listType, String[] stringElements, Image[] imageElements) {
        this(title, listType);
        strings.addAll(Arrays.asList(stringElements));
    }

    public void runSelectCommand() {
        commandListener.commandAction(selectCommand, this);
    }

    @Override
    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    @Override
    public int size() {
        return strings.size();
    }

    @Override
    public String getString(int elementNum) throws IndexOutOfBoundsException {
        return strings.get(elementNum);
    }

    @Override
    public Image getImage(int elementNum) throws IndexOutOfBoundsException {
        // TODO: write method logic
        return null;
    }

    @Override
    public int append(String stringPart, Image imagePart) throws NullPointerException {
        if (stringPart == null) {
            throw new NullPointerException();
        }
        strings.add(stringPart);
        return strings.size();
    }

    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) throws IndexOutOfBoundsException, NullPointerException {
        if (stringPart == null) {
            throw new NullPointerException();
        }
        // TODO: write method logic
    }

    @Override
    public void delete(int elementNum) throws IndexOutOfBoundsException {
        strings.remove(elementNum);
    }

    @Override
    public void deleteAll() {
        strings.clear();
    }

    @Override
    public void set(int elementNum, String stringPart, Image imagePart) throws IndexOutOfBoundsException, NullPointerException {
        if (stringPart == null) {
            throw new NullPointerException();
        }
        strings.set(elementNum, stringPart);
    }

    @Override
    public boolean isSelected(int elementNum) throws IndexOutOfBoundsException {
        return selectedElement == elementNum;
    }

    @Override
    public int getSelectedIndex() {
        return selectedElement;
    }

    @Override
    public int getSelectedFlags(boolean[] selectedArray_return) throws IllegalArgumentException, NullPointerException {
        // TODO: write method logic
        if (selectedArray_return == null) {
            throw new NullPointerException();
        }
        return 0;
    }

    @Override
    public void setSelectedIndex(int elementNum, boolean selected) throws IndexOutOfBoundsException {
        if (elementNum > strings.size() - 1) {
            selectedElement = 0;
        }
        else if (elementNum < 0) {
            selectedElement = strings.size() - 1;
        }
        else {
            selectedElement = elementNum;
        }
    }

    @Override
    public void setSelectedFlags(boolean[] selectedArray) throws IllegalArgumentException, NullPointerException {
        // TODO: write method logic
        if (selectedArray == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public void removeCommand(Command cmd) {
        super.removeCommand(cmd);
    }

    public void setSelectCommand(Command command) {
        selectCommand = command == null ? SELECT_COMMAND : command;
    }

    @Override
    public void setFitPolicy(int fitPolicy) throws IllegalArgumentException {
        this.fitPolicy = fitPolicy;
    }

    @Override
    public int getFitPolicy() {
        return fitPolicy;
    }

    @Override
    public void setFont(int elementNum, Font font) throws IndexOutOfBoundsException {
        // TODO: write method logic
    }

    @Override
    public Font getFont(int elementNum) throws IndexOutOfBoundsException {
        // TODO: write method logic
        return Font.getDefaultFont();
    }

    public void setRelatedWrapper(HangarListWrapper wrapper) {
        this.listWrapper = wrapper;
    }
}