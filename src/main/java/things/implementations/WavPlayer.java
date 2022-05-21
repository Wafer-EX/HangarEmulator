package things.implementations;

import javax.microedition.media.MediaException;
import javax.microedition.media.PlayerListener;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class WavPlayer extends ExtendedPlayer {
    private Clip clip;

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
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.STARTED, getMediaTime());
            }
        }
    }

    @Override
    public void stop() {
        if (getState() != PREFETCHED) {
            clip.stop();
            setState(PREFETCHED);
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.STOPPED, getMediaTime());
            }
        }
    }

    @Override
    public void close() {
        if (getState() != CLOSED) {
            clip.close();
            setState(CLOSED);
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.CLOSED, null);
            }
        }
    }

    @Override
    public void setLoopCount(int count) {
        // TODO: write method logic
    }

    @Override
    public int getLoopCount() {
        return 1;
    }
}