package things.implementations.additions;

import things.implementations.ExtendedPlayer;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import static javax.microedition.media.Player.PREFETCHED;
import static javax.microedition.media.Player.STARTED;

public class PlayerMetaEventListener implements MetaEventListener {
    private ExtendedPlayer player;

    public PlayerMetaEventListener(ExtendedPlayer player) {
        this.player = player;
    }

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 47 && player.getState() == STARTED) {
            player.setState(PREFETCHED);
            player.start();
        }
    }
}