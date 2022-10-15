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

public class TextBox extends Screen {
    private String string;
    private int maxSize;
    private int constraints;

    public TextBox(String title, String text, int maxSize, int constraints) throws IllegalArgumentException {
        this.setTitle(title);
        this.setString(text);
        this.setMaxSize(maxSize);
        this.setConstraints(constraints);
    }

    public String getString() {
        return string;
    }

    public void setString(String text) throws IllegalArgumentException {
        this.string = text;
    }

    public int getChars(char[] data) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        int charNum = 0;
        for (; charNum < data.length && charNum < string.length(); charNum++) {
            data[charNum] = string.charAt(charNum);
        }
        return charNum;
    }

    public void setChars(char[] data, int offset, int length) {
        // TODO: write method logic
    }

    public void insert(String src, int position) {
        // TODO: write method logic
    }

    public void insert(char[] data, int offset, int length, int position) {
        // TODO: write method logic
    }

    public void delete(int offset, int length) {
        // TODO: write method logic
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int setMaxSize(int maxSize) {
        return this.maxSize = maxSize;
    }

    public int size() {
        return string.length();
    }

    public int getCaretPosition() {
        // TODO: write method logic
        return 0;
    }

    public void setConstraints(int constraints) {
        this.constraints = constraints;
    }

    public int getConstraints() {
        return constraints;
    }

    public void setInitialInputMode(String characterSubset) {
        // TODO: write method logic
    }

    public void setTitle(String s) {
        super.setTitle(s);
    }

    public void setTicker(Ticker ticker) {
        super.setTicker(ticker);
    }
}