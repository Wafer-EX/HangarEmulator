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

import things.implementations.additions.MidiVolumeControl;
import things.implementations.additions.PlayerMetaEventListener;

import javax.microedition.media.*;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.InputStream;

public class MidiPlayer extends ExtendedPlayer {
    private Sequencer sequencer;

    public MidiPlayer(InputStream stream) {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(stream);
            sequencer.setLoopCount(1);
            sequencer.setMicrosecondPosition(0);
            sequencer.addMetaEventListener(new PlayerMetaEventListener(this));
            setState(PREFETCHED);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    @Override
    public long setMediaTime(long now) throws MediaException {
        if (getState() == UNREALIZED || getState() == CLOSED) {
            throw new IllegalStateException();
        }
        sequencer.setMicrosecondPosition(now);
        return now;
    }

    @Override
    public long getMediaTime() throws IllegalStateException {
        if (getState() == Player.CLOSED) {
            throw new IllegalStateException();
        }
        return sequencer.getMicrosecondPosition();
    }

    @Override
    public long getDuration() throws IllegalStateException {
        return sequencer.getMicrosecondLength();
    }

    @Override
    public void prefetch() {
        // TODO: write method logic
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        setState(PREFETCHED);
    }

    @Override
    public void start() {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        else if (getState() != STARTED) {
            if (getState() == UNREALIZED || getState() == REALIZED) {
                prefetch();
            }
            sequencer.start();
            setState(STARTED);
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.STARTED, getMediaTime());
            }
        }
    }

    @Override
    public void stop() {
        if (sequencer.isRunning()) {
            sequencer.stop();
            setState(PREFETCHED);
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.STOPPED, getMediaTime());
            }
        }
    }

    @Override
    public void close() {
        if (getState() != CLOSED) {
            setState(CLOSED);
            sequencer.close();
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.CLOSED, null);
            }
        }
    }

    @Override
    public void setLoopCount(int count) {
        if (getState() == STARTED || getState() == CLOSED) {
            throw new IllegalStateException();
        }
        else {
            if (count > 0) {
                sequencer.setLoopCount(count - 1);
            }
            else {
                sequencer.setLoopCount(count);
            }
        }
    }

    @Override
    public int getLoopCount() {
        if (getState() == STARTED) {
            throw new IllegalStateException();
        }
        return sequencer.getLoopCount();
    }

    @Override
    public Control getControl(String controlType) {
        switch (controlType) {
            case "VolumeControl":
                return new MidiVolumeControl(this);
            default:
                return null;
        }
    }
}