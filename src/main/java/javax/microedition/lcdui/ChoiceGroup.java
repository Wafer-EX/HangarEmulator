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

public class ChoiceGroup extends Item implements Choice {
    public ChoiceGroup(String label, int choiceType) throws IllegalArgumentException {
        // TODO: write constructor logic
    }

    public ChoiceGroup(String label, int choiceType, String[] stringElements, Image[] imageElements) throws NullPointerException, IllegalArgumentException {
        // TODO: write constructor logic
    }

    @Override
    public int size() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public String getString(int elementNum) {
        // TODO: write method logic
        return null;
    }

    @Override
    public Image getImage(int elementNum) {
        // TODO: write method logic
        return null;
    }

    @Override
    public int append(String stringPart, Image imagePart) {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) {
        // TODO: write method logic
    }

    @Override
    public void delete(int elementNum) {
        // TODO: write method logic
    }

    @Override
    public void deleteAll() {
        // TODO: write method logic
    }

    @Override
    public void set(int elementNum, String stringPart, Image imagePart) {
        // TODO: write method logic
    }

    @Override
    public boolean isSelected(int elementNum) {
        // TODO: write method logic
        return false;
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
        // TODO: write method logic
    }

    @Override
    public Font getFont(int elementNum) {
        // TODO: write method logic
        return null;
    }
}