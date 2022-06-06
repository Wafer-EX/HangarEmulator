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

import javax.swing.*;

public class List extends Screen implements Choice {
    public static final Command SELECT_COMMAND = new Command("", Command.SCREEN, 0);

    private final DefaultListModel listModel = new DefaultListModel();
    private final JList<DefaultListModel> list = new JList(listModel);
    private int listType;
    private Ticker ticker;
    private Command selectCommand = SELECT_COMMAND;
    private int fitPolicy = TEXT_WRAP_DEFAULT;

    public List(String title, int listType) {
        setTitle(title);
        this.listType = listType;
    }

    public List(String title, int listType, String[] stringElements, Image[] imageElements) {
        this(title, listType);
        for (int i = 0; i < stringElements.length; i++) {
            listModel.add(i, stringElements[i]);
        }
    }

    public JList getList() {
        return list;
    }

    @Override
    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    @Override
    public int size() {
        return listModel.getSize();
    }

    @Override
    public String getString(int elementNum) {
        return listModel.getElementAt(elementNum).toString();
    }

    @Override
    public Image getImage(int elementNum) {
        return null;
    }

    @Override
    public int append(String stringPart, Image imagePart) {
        listModel.addElement(stringPart);
        return listModel.size();
    }

    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) {
        // TODO: write method logic
    }

    @Override
    public void delete(int elementNum) {
        listModel.remove(elementNum);
    }

    @Override
    public void deleteAll() {
        listModel.clear();
    }

    @Override
    public void set(int elementNum, String stringPart, Image imagePart) {
        // TODO: write method logic
    }

    @Override
    public boolean isSelected(int elementNum) {
        return list.getSelectedIndex() == elementNum;
    }

    @Override
    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }

    @Override
    public int getSelectedFlags(boolean[] selectedArray_return) {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void setSelectedIndex(int elementNum, boolean selected) {
        list.setSelectedIndex(elementNum);
    }

    @Override
    public void setSelectedFlags(boolean[] selectedArray) {
        // TODO: write method logic
    }

    @Override
    public void removeCommand(Command cmd) {
        super.removeCommand(cmd);
    }

    public void setSelectCommand(Command command) {
        if (command == null) {
            selectCommand = SELECT_COMMAND;
        }
        else {
            selectCommand = command;
        }
    }

    @Override
    public void setFitPolicy(int fitPolicy) {
        this.fitPolicy = fitPolicy;
    }

    @Override
    public int getFitPolicy() {
        return fitPolicy;
    }

    @Override
    public void setFont(int elementNum, Font font) {
        // TODO: write method logic
    }

    @Override
    public Font getFont(int elementNum) {
        // TODO: write method logic
        return Font.getDefaultFont();
    }
}