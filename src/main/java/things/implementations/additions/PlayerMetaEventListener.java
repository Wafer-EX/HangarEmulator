package things.implementations.additions;

import things.implementations.ExtendedPlayer;

import javax.microedition.media.PlayerListener;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

public class PlayerMetaEventListener implements MetaEventListener {
    private ExtendedPlayer player;

    public PlayerMetaEventListener(ExtendedPlayer player) {
        this.player = player;
    }

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            for (var playerListener : player.playerListeners) {
                playerListener.playerUpdate(player, PlayerListener.END_OF_MEDIA, null);
            }
        }
    }
}