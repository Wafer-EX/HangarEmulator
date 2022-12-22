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

package things.ui.components;

import things.ui.listeners.events.HangarDisplayableEvent;
import things.ui.listeners.HangarDisplayableListener;
import things.ui.components.wrappers.HangarCanvasWrapper;
import things.ui.components.wrappers.HangarFormWrapper;
import things.ui.components.wrappers.HangarListWrapper;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HangarViewport extends JPanel {
    private final ArrayList<HangarDisplayableListener> displayableListeners = new ArrayList<>();
    private Displayable displayable = null;
    private HangarCanvasWrapper canvasWrapper = null;

    public HangarViewport() {
        super(new BorderLayout());

        var scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public HangarCanvasWrapper getCanvasWrapper() {
        return canvasWrapper;
    }

    public Displayable getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Displayable displayable) {
        this.removeAll();
        this.displayable = displayable;

        if (displayable.getCommands().size() > 0) {
            var displayableCommands = new HangarViewportCommands(displayable);
            this.add(displayableCommands, BorderLayout.SOUTH);
        }

        if (displayable instanceof Canvas canvas) {
            this.canvasWrapper = new HangarCanvasWrapper(canvas);
            this.add(canvasWrapper, BorderLayout.CENTER);
            SwingUtilities.invokeLater(canvas::showNotify);
        }
        else if (displayable instanceof List list) {
            var listWrapper = new HangarListWrapper(list);
            this.add(listWrapper, BorderLayout.CENTER);
        }
        else if (displayable instanceof Form form) {
            var formWrapper = new HangarFormWrapper(form);
            this.add(formWrapper, BorderLayout.CENTER);
        }
        else {
            // TODO: add more screens support
            throw new IllegalArgumentException();
        }

        for (var displayableListener : displayableListeners) {
            var displayableEvent = new HangarDisplayableEvent(displayable, HangarDisplayableEvent.SET);
            displayableListener.displayableStateChanged(displayableEvent);
        }
        this.revalidate();
        this.repaint();
    }

    public void addDisplayableListener(HangarDisplayableListener listener) {
        this.displayableListeners.add(listener);
    }
}