package things.implementations;

import things.implementations.additions.PlayerMetaEventListener;

import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
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
            sequencer.addMetaEventListener(new PlayerMetaEventListener(this));
            sequencer.setMicrosecondPosition(0);
            setState(PREFETCHED);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public long getMediaTime() throws IllegalStateException {
        if (getState() == Player.CLOSED) {
            throw  new IllegalStateException();
        }
        return sequencer.getMicrosecondLength();
    }

    @Override
    public void prefetch() {
        // TODO: write method logic
        setState(PREFETCHED);
    }

    @Override
    public void start() {
        if (getState() != STARTED) {
            if (getState() == UNREALIZED || getState() == REALIZED) {
                prefetch();
            }
            sequencer.start();
            setState(STARTED);
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.STARTED, null);
            }
        }
    }

    @Override
    public void stop() {
        if (getState() != PREFETCHED) {
            sequencer.stop();
            setState(PREFETCHED);
            for (var playerListener : playerListeners) {
                playerListener.playerUpdate(this, PlayerListener.STOPPED, null);
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
        if (getState() != STARTED) {
            if (count > 0) {
                sequencer.setLoopCount(count - 1);
            }
            else {
                sequencer.setLoopCount(count);
            }
        }
    }
}