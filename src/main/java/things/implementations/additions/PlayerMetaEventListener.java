package things.implementations.additions;

import things.implementations.ExtendedPlayer;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import static javax.microedition.media.Player.PREFETCHED;

public class PlayerMetaEventListener implements MetaEventListener {
    private ExtendedPlayer player;

    public PlayerMetaEventListener(ExtendedPlayer player) {
        this.player = player;
    }

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            player.setState(PREFETCHED);
            player.start();
        }
    }
}