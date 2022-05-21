package things.implementations;

import javax.microedition.media.Control;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedPlayer implements Player {
    private int currentState = UNREALIZED;
    public List<PlayerListener> playerListeners = new ArrayList<>();

    public abstract int getLoopCount();

    public void setState(int state) {
        this.currentState = state;
    }

    @Override
    public int getState() {
        return currentState;
    }

    @Override
    public void deallocate() {
        // TODO: write method logic
        setState(REALIZED);
    }

    @Override
    public void addPlayerListener(PlayerListener playerListener) throws IllegalStateException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        if (playerListener != null) {
            playerListeners.add(playerListener);
        }
    }

    @Override
    public void removePlayerListener(PlayerListener playerListener) throws IllegalStateException {
        if (getState() == CLOSED) {
            throw new IllegalStateException();
        }
        playerListeners.remove(playerListener);
    }

    @Override
    public Control[] getControls() {
        // TODO: write method logic
        return null;
    }

    @Override
    public Control getControl(String controlType) {
        // TODO: write method logic
        return null;
    }
}