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

package things.implementations.media;

import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedPlayer implements Player {
    private int currentState = UNREALIZED;
    private final List<PlayerListener> playerListeners = new ArrayList<>();

    public List<PlayerListener> getPlayerListeners() {
        return playerListeners;
    }

    public void setState(int state) {
        this.currentState = state;
    }

    @Override
    public void realize() throws IllegalStateException, MediaException, SecurityException {
        switch (currentState) {
            case CLOSED -> throw new IllegalStateException();
            case REALIZED, PREFETCHED, STARTED -> { }
            default -> setState(REALIZED);
        }
    }

    @Override
    public void prefetch() throws IllegalStateException, MediaException, SecurityException {
        if (getState() != STARTED) {
            switch (getState()) {
                case UNREALIZED -> realize();
                case CLOSED -> throw new IllegalStateException();
            }
            setState(PREFETCHED);
        }
    }

    @Override
    public void deallocate() throws IllegalStateException {
        switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            case UNREALIZED, REALIZED -> { }
            case STARTED -> {
                try {
                    stop();
                    setState(REALIZED);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            default -> setState(REALIZED);
        }
    }

    @Override
    public int getState() {
        return currentState;
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
}