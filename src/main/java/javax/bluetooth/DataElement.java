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

package javax.bluetooth;

public class DataElement {
    public static final int NULL = 0x00;

    public static final int U_INT_1 = 0x08;

    public static final int U_INT_2 = 0x09;

    public static final int U_INT_4 = 0x0A;

    public static final int U_INT_8 = 0x0B;

    public static final int U_INT_16 = 0x0C;

    public static final int INT_1 = 0x10;

    public static final int INT_2 = 0x11;

    public static final int INT_4 = 0x12;

    public static final int INT_8 = 0x13;

    public static final int INT_16 = 0x14;

    public static final int URL = 0x40;

    public static final int UUID = 0x18;

    public static final int BOOL = 0x28;

    public static final int STRING = 0x20;

    public static final int DATSEQ = 0x30;

    public static final int DATALT = 0x38;

    public DataElement(int valueType) {
        // TODO: write constructor logic
    }

    public DataElement(boolean bool) {
        // TODO: write constructor logic
    }

    public DataElement(int valueType, long value) {
        // TODO: write constructor logic
    }

    public DataElement(int valueType, Object value) {
        // TODO: write constructor logic
    }

    public void addElement(DataElement elem) {
        // TODO: write method logic
    }

    public void insertElementAt(DataElement elem, int index) {
        // TODO: write method logic
    }

    public int getSize() {
        // TODO: write method logic
        return 0;
    }

    public boolean removeElement(DataElement elem) {
        // TODO: write method logic
        return false;
    }

    public int getDataType() {
        // TODO: write method logic
        return 0;
    }

    public long getLong() {
        // TODO: write method logic
        return 0;
    }

    public boolean getBoolean() {
        // TODO: write method logic
        return false;
    }

    public Object getValue() {
        // TODO: write method logic
        return null;
    }
}