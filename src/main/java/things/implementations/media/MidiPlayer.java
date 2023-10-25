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

import things.utils.AudioUtils;
import things.implementations.media.control.MidiVolumeControl;

import javax.microedition.media.*;
import javax.sound.midi.Sequencer;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MidiPlayer extends AbstractPlayer {
    private Sequencer sequencer;
    private byte[] audioSource;

    public MidiPlayer(InputStream stream) {
        try {
            audioSource = stream.readAllBytes();
            setState(UNREALIZED);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    @Override
    public void realize() throws IllegalStateException, MediaException, SecurityException {
        switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            case UNREALIZED -> {
                try {
                    if (sequencer == null) {
                        sequencer = AudioUtils.getSequencerWithSoundbank();
                        sequencer.open();
                        sequencer.addMetaEventListener(meta -> {
                            // TODO: check it
                            if (meta.getType() == 47) {
                                if (sequencer.isRunning()) {
                                    for (var playerListener : getPlayerListeners()) {
                                        playerListener.playerUpdate(this, PlayerListener.STARTED, sequencer.getMicrosecondLength());
                                    }
                                }
                                else {
                                    for (var playerListener : getPlayerListeners()) {
                                        playerListener.playerUpdate(this, PlayerListener.END_OF_MEDIA, null);
                                    }
                                    setState(PREFETCHED);
                                }
                            }
                        });
                    }
                    setState(REALIZED);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    public void prefetch() throws IllegalStateException, MediaException, SecurityException {
        switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            case STARTED, PREFETCHED -> { }
            default -> {
                try {
                    if (getState() == UNREALIZED) {
                        realize();
                    }
                    if (sequencer.getSequence() == null) {
                        sequencer.setSequence(new ByteArrayInputStream(audioSource));
                    }
                    sequencer.setMicrosecondPosition(0);
                    setState(PREFETCHED);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    throw new MediaException();
                }
            }
        }
    }

    @Override
    public void start() throws IllegalStateException, MediaException, SecurityException {
        switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            case STARTED -> { }
            default -> {
                if (getState() == UNREALIZED || getState() == REALIZED) {
                    prefetch();
                }
                sequencer.start();
                setState(STARTED);
                for (var playerListener : getPlayerListeners()) {
                    playerListener.playerUpdate(this, PlayerListener.STARTED, sequencer.getMicrosecondPosition());
                }
            }
        }
    }

    @Override
    public void stop() throws IllegalStateException, MediaException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        else if (sequencer.isRunning()) {
            sequencer.stop();
            setState(PREFETCHED);
            for (var playerListener : getPlayerListeners()) {
                playerListener.playerUpdate(this, PlayerListener.STOPPED, sequencer.getMicrosecondPosition());
            }
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
    public long setMediaTime(long now) throws IllegalStateException, MediaException {
        switch (getState()) {
            case UNREALIZED, CLOSED -> throw new IllegalStateException();
            default -> {
                sequencer.setMicrosecondPosition(now);
                var newMediaTime = sequencer.getMicrosecondPosition();
                for (var playerListener : getPlayerListeners()) {
                    playerListener.playerUpdate(this, PlayerListener.DURATION_UPDATED, newMediaTime);
                }
                return newMediaTime;
            }
        }
    }

    @Override
    public long getMediaTime() throws IllegalStateException {
        return switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            default -> sequencer.getMicrosecondPosition();
        };
    }

    @Override
    public long getDuration() throws IllegalStateException {
        return switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            default -> sequencer.getMicrosecondLength();
        };
    }

    @Override
    public String getContentType() throws IllegalStateException {
        return switch (getState()) {
            case UNREALIZED, CLOSED -> throw new IllegalStateException();
            default -> "audio/midi";
        };
    }

    @Override
    public void setLoopCount(int count) throws IllegalArgumentException, IllegalStateException {
        switch (getState()) {
            case STARTED, CLOSED -> throw new IllegalStateException();
            default -> {
                if (getState() == UNREALIZED) {
                    try {
                        realize();
                    }
                    catch (MediaException ignored) { }
                }
                switch (count) {
                    case -1 -> sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
                    case 0 -> throw new IllegalArgumentException();
                    default -> sequencer.setLoopCount(count - 1);
                }
            }
        }
    }

    @Override
    public Control[] getControls() throws IllegalStateException {
        // TODO: add controls to array
        return switch (getState()) {
            case UNREALIZED, CLOSED -> throw new IllegalStateException();
            default -> new Control[0];
        };
    }

    @Override
    public Control getControl(String controlType) throws IllegalArgumentException, IllegalStateException {
        // TODO: add ToneControl
        return switch (getState()) {
            case UNREALIZED, CLOSED -> throw new IllegalStateException();
            default -> switch (controlType) {
                case "ToneControl" -> null;
                case "VolumeControl" -> new MidiVolumeControl(this);
                default -> throw new IllegalArgumentException();
            };
        };
    }
}