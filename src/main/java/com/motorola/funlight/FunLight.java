/*
 * Copyright 2024 Wafer EX
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

package com.motorola.funlight;

public class FunLight {
    public static final int BLACK = 0x00000000;
    public static final int BLANK = 0;
    public static final int BLUE = 0x0000FFFF;
    public static final int GREEN = 0x0000FF00;
    public static final int MAGENTA = 0x00FF00FF;
    public static final int OFF = 0x00000000;
    public static final int ON = 0x00FFFFFF;
    public static final int RED = 0x00FF0000;
    public static final int WHITE = 0x00FFFFFF;
    public static final int YELLOW = 0x00FFFF00;
    public static final int QUEUED = 1;
    public static final int SUCCESS = 0;
    public static final int IGNORED = 2;

    public static int getControl() {
        return QUEUED;
    }

    public static Region getRegion(int ID) {
        return new Region() {
            @Override
            public int getColor() {
                return BLACK;
            }

            @Override
            public int getControl() {
                return QUEUED;
            }

            @Override
            public int getID() {
                return BLANK;
            }

            @Override
            public void releaseControl() { }

            @Override
            public int setColor(int color) {
                return QUEUED;
            }

            @Override
            public int setColor(byte red, byte green, byte blue) {
                return QUEUED;
            }

            @Override
            public String toString() {
                return "Blank";
            }
        };
    }

    public static int[] getRegionsIDs() {
        return null;
    }

    public static Region[] getRegions() {
        return null;
    }

    public static void releaseControl() { }

    public static int setColor(int color) {
        return QUEUED;
    }

    public static int setColor(byte red, byte green, byte blue) {
        return QUEUED;
    }
}