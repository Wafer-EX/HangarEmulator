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
import java.io.BufferedInputStream;
import java.io.InputStream;

public class WavPlayer extends ExtendedPlayer {
    private Clip clip;
    private int loopCount;

    public WavPlayer(InputStream stream) {
        try {
            var bufferedInputStream = new BufferedInputStream(stream);
            var audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(0);
            setState(PREFETCHED);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public long setMediaTime(long now) throws MediaException {
        if (getState() == UNREALIZED || getState() == CLOSED) {
            throw new IllegalStateException();
        }
        clip.setMicrosecondPosition(now);
        return now;
    }

    @Override
    public long getMediaTime() {
        return clip.getMicrosecondLength();
    }

    @Override
    public long getDuration() throws IllegalStateException {
        return clip.getMicrosecondLength();
    }

    @Override
    public String getContentType() throws IllegalStateException {
        return "audio/x-wav";
    }

    @Override
    public void prefetch() {
        setState(PREFETCHED);
    }

    @Override
    public void start() {
        if (getState() != STARTED) {
            if (getState() == UNREALIZED || getState() == REALIZED) {
                prefetch();
            }
            clip.setMicrosecondPosition(0);
            clip.start();
            setState(STARTED);
            for (var playerListener : getPlayerListeners()) {
                playerListener.playerUpdate(this, PlayerListener.STARTED, getMediaTime());
            }
        }
    }

    @Override
    public void stop() {
        if (getState() != PREFETCHED) {
            clip.stop();
            setState(PREFETCHED);
            for (var playerListener : getPlayerListeners()) {
                playerListener.playerUpdate(this, PlayerListener.STOPPED, getMediaTime());
            }
        }
    }

    @Override
    public void deallocate() {
        // TODO: rewrite method logic
        setState(REALIZED);
    }

    @Override
    public void close() {
        if (getState() != CLOSED) {
            clip.close();
            setState(CLOSED);
            for (var playerListener : getPlayerListeners()) {
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
                clip.loop(count - 1);
                loopCount = count - 1;
            }
            else {
                clip.loop(count);
                loopCount = count;
            }
        }
    }

    @Override
    public Control[] getControls() {
        return new Control[0];
    }

    @Override
    public Control getControl(String controlType) {
        return switch (controlType) {
            case "VolumeControl" -> new WavVolumeControl(this);
            default -> null;
        };
    }
}