package things;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileInputStream;

public final class HangarAudio {
    private static Soundbank soundbank;

    public static Soundbank loadSoundbank(File soundbankFile) {
        try {
            var soundbankInputStream = new FileInputStream(soundbankFile);
            soundbank = MidiSystem.getSoundbank(soundbankInputStream);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return soundbank;
    }

    public static Sequencer getSequencer() {
        try {
            if (soundbank != null) {
                var sequencer = MidiSystem.getSequencer(false);
                var synthesizer = MidiSystem.getSynthesizer();

                synthesizer.open();
                synthesizer.loadAllInstruments(soundbank);
                sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());

                return sequencer;
            }
            else {
                return MidiSystem.getSequencer();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}