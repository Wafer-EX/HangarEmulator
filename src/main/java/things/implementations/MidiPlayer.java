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

import things.HangarAudio;
import things.implementations.additions.MidiVolumeControl;

import javax.microedition.media.*;
import javax.sound.midi.Sequencer;
import java.io.InputStream;

public class MidiPlayer extends ExtendedPlayer {
    private Sequencer sequencer;

    public MidiPlayer(InputStream stream) {
        try {
            sequencer = HangarAudio.getSequencerWithSoundbank();
            sequencer.open();
            sequencer.setSequence(stream);
            sequencer.setLoopCount(1);
            sequencer.setMicrosecondPosition(0);
            sequencer.addMetaEventListener(meta -> {
                if (meta.getType() == 47) {
                    for (var playerListener : getPlayerListeners()) {
                        playerListener.playerUpdate(this, PlayerListener.END_OF_MEDIA, null);
                    }
                    if (sequencer.getLoopCount() > 0 || sequencer.getLoopCount() == -1) {
                        for (var playerListener : getPlayerListeners()) {
                            playerListener.playerUpdate(this, PlayerListener.STARTED, getMediaTime());
                        }
                    }
                }
            });
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
    public void start() throws MediaException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        else if (getState() != STARTED) {
            if (getState() == UNREALIZED || getState() == REALIZED) {
                prefetch();
            }
            sequencer.start();
            setState(STARTED);
            for (var playerListener : getPlayerListeners()) {
                playerListener.playerUpdate(this, PlayerListener.STARTED, getMediaTime());
            }
        }
    }

    @Override
    public void stop() throws IllegalStateException, MediaException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        if (sequencer.isRunning()) {
            sequencer.stop();
            setState(PREFETCHED);
            for (var playerListener : getPlayerListeners()) {
                playerListener.playerUpdate(this, PlayerListener.STOPPED, getMediaTime());
            }
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
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            default -> setState(REALIZED);
        }
    }

    @Override
    public void close() {
        if (getState() != CLOSED) {
            setState(CLOSED);
            sequencer.close();
            for (var playerListener : getPlayerListeners()) {
                playerListener.playerUpdate(this, PlayerListener.CLOSED, null);
            }
        }
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
    public String getContentType() throws IllegalStateException {
        return "audio/midi";
    }

    @Override
    public void setLoopCount(int count) throws IllegalArgumentException, IllegalStateException {
        if (getState() == STARTED || getState() == CLOSED) {
            throw new IllegalStateException();
        }
        switch (count) {
            case -1 -> sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            case 0 -> throw new IllegalArgumentException();
            default -> sequencer.setLoopCount(count - 1);
        }
    }

    @Override
    public Control[] getControls() throws IllegalStateException {
        // TODO: add controls to array
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        return new Control[0];
    }

    @Override
    public Control getControl(String controlType) throws IllegalArgumentException, IllegalStateException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        return switch (controlType) {
            // TODO: add ToneControl
            case "ToneControl" -> null;
            case "VolumeControl" -> new MidiVolumeControl(this);
            default -> throw new IllegalArgumentException();
        };
    }
}