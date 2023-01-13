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

package javax.microedition.lcdui;

public class Gauge extends Item {
    public static final int INDEFINITE = -1;
    public static final int CONTINUOUS_IDLE = 0;
    public static final int INCREMENTAL_IDLE = 1;
    public static final int CONTINUOUS_RUNNING = 2;
    public static final int INCREMENTAL_UPDATING = 3;

    public Gauge(String label, boolean interactive, int maxValue, int initialValue) {
        // TODO: write constructor logic
    }

    @Override
    public void setLabel(String label) {
        // TODO: write method logic
    }

    @Override
    public void setLayout(int layout) {
        // TODO: write method logic
    }

    @Override
    public void addCommand(Command cmd) {
        // TODO: write method logic
    }

    @Override
    public void setItemCommandListener(ItemCommandListener l) {
        // TODO: write method logic
    }

    @Override
    public void setPreferredSize(int width, int height) {
        // TODO: write method logic
    }

    @Override
    public void setDefaultCommand(Command cmd) {
        // TODO: write method logic
    }

    public void setValue(int value) {
        // TODO: write method logic
    }

    public int getValue() {
        // TODO: write method logic
        return 0;
    }

    public void setMaxValue(int maxValue) {
        // TODO: write method logic
    }

    public int getMaxValue() {
        // TODO: write method logic
        return 0;
    }

    public boolean isInteractive() {
        // TODO: write method logic
        return false;
    }
}