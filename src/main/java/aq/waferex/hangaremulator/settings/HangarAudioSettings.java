/*
 * Copyright 2024 Wafer EX
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

package aq.waferex.hangaremulator.settings;

import aq.waferex.hangaremulator.utils.AudioUtils;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HangarAudioSettings {
    private File soundbankFile = null;

    public HangarAudioSettings() { }

    public File getSoundbankFile() {
        return soundbankFile;
    }

    public void setSoundbankFile(File path) throws IOException, InvalidMidiDataException {
        if (path != null) {
            var soundbankInputStream = new FileInputStream(path);
            var soundbank = MidiSystem.getSoundbank(soundbankInputStream);
            AudioUtils.setSoundbank(soundbank);
        }
        this.soundbankFile = path;
    }
}