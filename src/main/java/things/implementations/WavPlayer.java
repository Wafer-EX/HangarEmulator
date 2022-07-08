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

import things.implementations.additions.WavVolumeControl;

import javax.microedition.media.Control;
import javax.microedition.media.MediaException;
import javax.microedition.media.PlayerListener;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WavPlayer extends ExtendedPlayer {
    private Clip clip;
    private byte[] audioSource;

    public WavPlayer(InputStream stream) {
        try {
            audioSource = stream.readAllBytes();
            setState(UNREALIZED);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void realize() throws IllegalStateException, MediaException, SecurityException {
        switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            case UNREALIZED -> {
                try {
                    if (clip == null) {
                        clip = AudioSystem.getClip();
                    }
                    setState(REALIZED);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
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
                    var audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioSource));
                    clip.open(audioInputStream);
                    clip.setMicrosecondPosition(0);
                    setState(PREFETCHED);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
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
                clip.start();
                setState(STARTED);
                for (var playerListener : getPlayerListeners()) {
                    playerListener.playerUpdate(this, PlayerListener.STARTED, clip.getMicrosecondPosition());
                }
            }
        }
    }

    @Override
    public void stop() throws IllegalStateException, MediaException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        if (clip.isRunning()) {
            clip.stop();
            setState(PREFETCHED);
            for (var playerListener : getPlayerListeners()) {
                playerListener.playerUpdate(this, PlayerListener.STOPPED, clip.getMicrosecondPosition());
            }
        }
    }

    @Override
    public void close() {
        if (getState() != CLOSED) {
            setState(CLOSED);
            clip.close();
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
                clip.setMicrosecondPosition(now);
                var newMediaTime = clip.getMicrosecondPosition();
                for (var playerListener : getPlayerListeners()) {
                    playerListener.playerUpdate(this, PlayerListener.DURATION_UPDATED, newMediaTime);
                }
                return newMediaTime;
            }
        }
    }

    @Override
    public long getMediaTime() throws IllegalStateException  {
        return switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            default -> clip.getMicrosecondPosition();
        };
    }

    @Override
    public long getDuration() throws IllegalStateException {
        return switch (getState()) {
            case CLOSED -> throw new IllegalStateException();
            default -> clip.getMicrosecondLength();
        };
    }

    @Override
    public String getContentType() throws IllegalStateException {
        return switch (getState()) {
            case UNREALIZED, CLOSED -> throw new IllegalStateException();
            default -> "audio/x-wav";
        };
    }

    @Override
    public void setLoopCount(int count) throws IllegalArgumentException, IllegalStateException {
        if (getState() == STARTED || getState() == CLOSED) {
            throw new IllegalStateException();
        }
        switch (count) {
            case -1 -> clip.loop(Clip.LOOP_CONTINUOUSLY);
            case 0 -> throw new IllegalArgumentException();
            default -> clip.loop(count - 1);
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
                case "VolumeControl" -> new WavVolumeControl(this);
                default -> throw new IllegalArgumentException();
            };
        };
    }
}