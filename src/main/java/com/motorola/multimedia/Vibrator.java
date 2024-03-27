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

package com.motorola.multimedia;

import java.util.TimerTask;

public class Vibrator extends TimerTask {
    // TODO: set values
    public static final int MAX_VIBRATE_TIME = 0;
    public static final int MIN_PAUSE_TIME = 0;
    public static final int VIBRATE_SILENT = 0;
    public static final int VIBRATE_SHORT = 0;
    public static final int VIBRATE_LONG = 0;
    public static final int VIBRATE_2SHORT = 0;
    public static final int VIBRATE_SHORT_LONG = 0;
    public static final int VIBRATE_PULSE = 0;

    @Override
    public void run() {
        // TODO: use it somewhere
    }

    public static void vibrateFor(int timeInMs) {
        // TODO: write method logic
    }

    public static void vibratePeriodically(int timeInMs) {
        // TODO: write method logic
    }

    public static void vibratePeriodically(int timeOnInMs, int timeOffInMs) {
        // TODO: write method logic
    }

    public static void vibratorOff() {
        // TODO: write method logic
    }

    public static void vibratorOn() {
        // TODO: write method logic
    }

    public static void setVibrateTone(int tone) {
        // TODO: write method logic
    }
}