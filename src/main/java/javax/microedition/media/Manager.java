package javax.microedition.media;

import things.implementations.MidiPlayer;

import java.io.InputStream;

public final class Manager implements Controllable {
    public static Player createPlayer(InputStream stream, String type) {
        if (type == "audio/midi") {
            Player player = new MidiPlayer(stream);
            return player;
        }
        else {
            return null;
        }
    }
}