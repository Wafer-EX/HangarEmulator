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

package things.implementations;

import javax.microedition.media.Control;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedPlayer implements Player {
    private int currentState = UNREALIZED;
    public List<PlayerListener> playerListeners = new ArrayList<>();

    public abstract int getLoopCount();

    public void setState(int state) {
        this.currentState = state;
    }

    @Override
    public void realize() throws MediaException {
        if (getState() == CLOSED) {
            throw new MediaException();
        }
        setState(REALIZED);
    }

    @Override
    public int getState() {
        return currentState;
    }

    @Override
    public void deallocate() {
        // TODO: write method logic
        setState(REALIZED);
    }

    @Override
    public void addPlayerListener(PlayerListener playerListener) throws IllegalStateException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        if (playerListener != null) {
            playerListeners.add(playerListener);
        }
    }

    @Override
    public void removePlayerListener(PlayerListener playerListener) throws IllegalStateException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        playerListeners.remove(playerListener);
    }

    @Override
    public Control[] getControls() {
        // TODO: write method logic
        return null;
    }

    @Override
    public Control getControl(String controlType) {
        // TODO: write method logic
        return null;
    }
}