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

public class Gauge extends Item {
    public static final int INDEFINITE = -1;
    public static final int CONTINUOUS_IDLE = 0;
    public static final int INCREMENTAL_IDLE = 1;
    public static final int CONTINUOUS_RUNNING = 2;
    public static final int INCREMENTAL_UPDATING = 3;

    private final boolean isInteractive;
    private int value;
    private int maxValue;

    public Gauge(String label, boolean interactive, int maxValue, int initialValue) {
        if (interactive && maxValue < 0) {
            throw new IllegalArgumentException();
        }

        this.isInteractive = interactive;

        setMaxValue(maxValue);
        setValue(initialValue);
        setLabel(label);
    }

    @Override
    public void setLabel(String label) {
        super.setLabel(label);
    }

    @Override
    public void setLayout(int layout) {
        super.setLayout(layout);
    }

    @Override
    public void addCommand(Command cmd) {
        super.addCommand(cmd);
    }

    @Override
    public void setItemCommandListener(ItemCommandListener l) {
        super.setItemCommandListener(l);
    }

    @Override
    public void setPreferredSize(int width, int height) {
        super.setPreferredSize(width, height);
    }

    @Override
    public void setDefaultCommand(Command cmd) {
        super.setDefaultCommand(cmd);
    }

    public void setValue(int value) {
        if (!isInteractive && maxValue == INDEFINITE && (value != CONTINUOUS_IDLE && value != INCREMENTAL_IDLE && value != CONTINUOUS_RUNNING && value != INCREMENTAL_UPDATING)) {
            throw new IllegalArgumentException();
        }
        else {
            if (value < 0) {
                value = 0;
            }
            if (value > maxValue) {
                value = maxValue;
            }
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setMaxValue(int maxValue) {
        if (isInteractive && maxValue < 0) {
            throw new IllegalArgumentException();
        }
        if (!isInteractive && maxValue < 0 && maxValue != INDEFINITE) {
            throw new IllegalArgumentException();
        }
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public boolean isInteractive() {
        return isInteractive;
    }
}