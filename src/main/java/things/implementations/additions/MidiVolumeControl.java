package things.implementations.additions;

import things.implementations.MidiPlayer;

import javax.microedition.media.Control;
import javax.microedition.media.control.VolumeControl;

public class MidiVolumeControl implements VolumeControl {
    private final MidiPlayer player;
    private boolean mute = false;
    private int level;

    public MidiVolumeControl(MidiPlayer player) {
        this.player = player;
    }

    @Override
    public void setMute(boolean mute) {
        var sequencer = player.getSequencer();
        var tracksCount = sequencer.getSequence().getTracks().length;
        for (int i = 0; i < tracksCount; i++) {
            sequencer.setTrackMute(i, mute);
        }
        this.mute = mute;
    }

    @Override
    public boolean isMuted() {
        return mute;
    }

    @Override
    public int setLevel(int level) {
        if (level < 0) {
            level = 0;
        }
        else if (level > 100) {
            level = 100;
        }
        var sequencer = player.getSequencer();
        return this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Control[] getControls() {
        // TODO: write method logic
        return new Control[0];
    }

    @Override
    public Control getControl(String controlType) {
        // TODO: write method logic
        return null;
    }
}