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

package aq.waferex.hangaremulator.settings;

import aq.waferex.hangaremulator.enums.GraphicsEngines;
import aq.waferex.hangaremulator.enums.ScalingModes;

import java.awt.*;

public class HangarGraphicsSettings {
    private boolean isCanvasClearingEnabled = false;
    private boolean isAntiAliasingEnabled = false;
    private GraphicsEngines graphicsEngine = GraphicsEngines.Swing;
    private int frameRate = 60;
    private ScalingModes scalingMode = ScalingModes.None;
    private Dimension resolution = new Dimension(240, 320);

    public HangarGraphicsSettings() { }

    public HangarGraphicsSettings(boolean isCanvasClearingEnabled, boolean isAntiAliasingEnabled, GraphicsEngines graphicsEngine, int frameRate, ScalingModes scalingMode, Dimension resolution) {
        setCanvasClearing(isAntiAliasingEnabled);
        setAntiAliasing(isAntiAliasingEnabled);
        setGraphicsEngine(graphicsEngine);
        setFrameRate(frameRate);
        setScalingMode(scalingMode);
        setResolution(resolution);
    }

    public boolean getCanvasClearing() {
        return isCanvasClearingEnabled;
    }

    public void setCanvasClearing(boolean isEnabled) {
        isCanvasClearingEnabled = isEnabled;
    }

    public boolean getAntiAliasing() {
        return isAntiAliasingEnabled;
    }

    public void setAntiAliasing(boolean isEnabled) {
        isAntiAliasingEnabled = isEnabled;
    }

    public GraphicsEngines getGraphicsEngine() {
        return graphicsEngine;
    }

    public void setGraphicsEngine(GraphicsEngines graphicsEngine) {
        this.graphicsEngine = graphicsEngine;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public ScalingModes getScalingMode() {
        return scalingMode;
    }

    public void setScalingMode(ScalingModes scalingMode) {
        this.scalingMode = scalingMode;
    }

    public Dimension getResolution() {
        return resolution;
    }

    public void setResolution(Dimension resolution) {
        this.resolution = resolution;
    }
}