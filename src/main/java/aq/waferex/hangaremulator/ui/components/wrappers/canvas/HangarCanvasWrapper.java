/*
 * Copyright 2023 Kirill Lomakin
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

package aq.waferex.hangaremulator.ui.components.wrappers.canvas;

import aq.waferex.hangaremulator.HangarState;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class HangarCanvasWrapper extends JPanel {
    protected final Canvas canvas;
    private Runnable callSerially;
    private Timer serialCallTimer = new Timer();

    protected HangarCanvasWrapper(Canvas canvas) {
        super(new CardLayout());
        this.canvas = canvas;
        this.refreshSerialCallTimer();
    }

    public void setCallSerially(Runnable runnable) {
        this.callSerially = runnable;
    }

    public void refreshSerialCallTimer() {
        serialCallTimer.cancel();
        serialCallTimer.purge();
        serialCallTimer = new Timer();

        var frameRateInMilliseconds = HangarState.frameRateInMilliseconds();
        if (frameRateInMilliseconds >= 0) {
            serialCallTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (callSerially != null) {
                        callSerially.run();
                    }
                }
            }, 0, frameRateInMilliseconds);
        }
    }

    public abstract Rectangle getDisplayedArea();

    public abstract double getScaleFactor();
}