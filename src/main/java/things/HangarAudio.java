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

package things;

import things.ui.HangarFrame;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;

public final class HangarAudio {
    private static Soundbank soundbank;

    public static void loadSoundbank(File soundbankFile) {
        try {
            var soundbankInputStream = new FileInputStream(soundbankFile);
            soundbank = MidiSystem.getSoundbank(soundbankInputStream);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(HangarFrame.getInstance(), "The file format is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setSoundbank(Soundbank soundbank) {
        HangarAudio.soundbank = soundbank;
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