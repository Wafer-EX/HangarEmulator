package javax.microedition.media;

import things.implementations.MidiPlayer;

import java.io.InputStream;

public final class Manager {
    public static Player createPlayer(InputStream stream, String type) {
        Player player = null;
        switch (type) {
            case "audio/midi":
                player = new MidiPlayer(stream);
                break;
            // TODO: write logic for other types
        }
        return player;
    }
}