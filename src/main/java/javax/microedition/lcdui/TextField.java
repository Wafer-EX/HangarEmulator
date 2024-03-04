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

import java.util.Arrays;

public class TextField extends Item {
    public static final int ANY = 0;
    public static final int EMAILADDR = 1;
    public static final int NUMERIC = 2;
    public static final int PHONENUMBER = 3;
    public static final int URL = 4;
    public static final int DECIMAL = 5;
    public static final int PASSWORD = 0x10000;
    public static final int UNEDITABLE = 0x20000;
    public static final int SENSITIVE = 0x40000;
    public static final int NON_PREDICTIVE = 0x80000;
    public static final int INITIAL_CAPS_WORD = 0x100000;
    public static final int INITIAL_CAPS_SENTENCE = 0x200000;
    public static final int CONSTRAINT_MASK = 0xFFFF;

    private String text;
    private int maxSize;
    private int constraints;

    public TextField(String label, String text, int maxSize, int constraints) {
        this.setLabel(label);
        this.text = text;
        this.maxSize = maxSize;
        this.constraints = constraints;
    }

    public String getString() {
        return text;
    }

    public void setString(String text) throws IllegalArgumentException {
        this.text = text;
    }

    public int getChars(char[] data) throws ArrayIndexOutOfBoundsException, NullPointerException  {
        if (data == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = text.charAt(i);
        }
        return data.length;
    }

    public void setChars(char[] data, int offset, int length) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO: check it
        var chars = text.toCharArray();
        for (int i = offset, j = 0; i < length + offset; i++, j++) {
            chars[i] = data[j];
        }
        text = Arrays.toString(chars);
    }

    public void insert(String src, int position) throws IllegalArgumentException, NullPointerException {
        // TODO: check it
        text = text.substring(0, position) + src + text.substring(position + 1);
    }

    public void insert(char[] data, int offset, int length, int position) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        // TODO: write method logic
    }

    public void delete(int offset, int length) throws IllegalArgumentException, StringIndexOutOfBoundsException {
        // TODO: check it
        text = text.substring(0, offset) + text.substring(offset + length + 1);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int setMaxSize(int maxSize) throws IllegalArgumentException {
        if (maxSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.maxSize = maxSize;
        return maxSize;
    }

    public int size() {
        return text.length();
    }

    public int getCaretPosition() {
        // TODO: write method logic
        return 0;
    }

    public void setConstraints(int constraints) throws IllegalArgumentException {
        this.constraints = constraints;
    }

    public int getConstraints() {
        return constraints;
    }

    public void setInitialInputMode(String characterSubset) {
        // TODO: write method logic
    }
}