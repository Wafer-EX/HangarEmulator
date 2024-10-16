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

package javax.microedition.midlet;

import aq.waferex.hangaremulator.HangarState;

public abstract class MIDlet {
    protected MIDlet() { }

    public abstract void startApp() throws MIDletStateChangeException;

    public abstract void pauseApp();

    public abstract void destroyApp(boolean unconditional) throws MIDletStateChangeException;

    public void notifyDestroyed() {
        System.exit(0);
    }

    public final void notifyPaused() {
        // TODO: render OpenGL canvas in 60 fps, remove it
        HangarState.getMainFrame().getViewport().repaint();
    }

    public final String getAppProperty(String key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        return HangarState.getProperties().getProperty(key);
    }

    public final void resumeRequest() {
        // TODO: write method logic
    }

    public final int checkPermission(String permission) {
        // TODO: check it
        return 1;
    }
}