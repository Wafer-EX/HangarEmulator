package things.implementations;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.InputStream;

public class MidiPlayer implements javax.microedition.media.Player {
    private Sequencer sequencer;
    private int currentState;

    public MidiPlayer(InputStream stream) {
        try {
            this.sequencer = MidiSystem.getSequencer();
            this.sequencer.open();
            this.sequencer.setSequence(stream);
            currentState = PREFETCHED;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int getState() {
        return currentState;
    }

    @Override
    public void start() {
        if (currentState != STARTED) {
            sequencer.setMicrosecondPosition(0);
            sequencer.start();
            currentState = STARTED;
        }
    }

    @Override
    public void stop() {
        sequencer.stop();
        currentState = REALIZED;
    }
}