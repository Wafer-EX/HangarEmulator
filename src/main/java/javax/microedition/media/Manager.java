package javax.microedition.media;

import things.implementations.MidiPlayer;
import things.implementations.WavPlayer;

import java.io.InputStream;

public final class Manager {
    public static Player createPlayer(InputStream stream, String type) {
        // TODO: write logic for other types
        switch (type) {
            case "audio/midi":
                return new MidiPlayer(stream);
            case "audio/x-wav":
                return new WavPlayer(stream);
            default:
                return null;
        }
    }
}