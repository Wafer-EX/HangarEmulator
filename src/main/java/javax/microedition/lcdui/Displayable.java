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

import things.HangarPanel;
import things.HangarState;

import javax.swing.*;

public abstract class Displayable {
    private Ticker ticker;
    private CommandListener commandListener;

    public String getTitle() {
        var panel = HangarPanel.getInstance();
        var frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        return frame.getTitle();
    }

    public void setTitle(String s) {
        var panel = HangarPanel.getInstance();
        var frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.setTitle(s);
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public boolean isShown() {
        return HangarPanel.getDisplayable() == this;
    }

    public void addCommand(Command cmd) {
        if (cmd == null) {
            throw new NullPointerException();
        }
    }

    public void removeCommand(Command cmd) { }

    public void setCommandListener(CommandListener l) {
        commandListener = l;
    }

    public int getWidth() {
        return HangarState.getResolution().width;
    }

    public int getHeight() {
        return HangarState.getResolution().height;
    }

    protected void sizeChanged(int w, int h) { }
}