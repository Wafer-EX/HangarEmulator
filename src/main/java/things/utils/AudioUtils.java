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

package things.utils;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;

public final class AudioUtils {
    private static Soundbank soundbank;

    public static void setSoundbank(Soundbank soundbank) {
        AudioUtils.soundbank = soundbank;
    }

    public static Sequencer getSequencerWithSoundbank() {
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
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}