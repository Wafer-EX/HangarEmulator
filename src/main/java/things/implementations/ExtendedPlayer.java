package things.implementations;

import javax.microedition.media.Player;

public abstract class ExtendedPlayer implements Player {
    private int currentState;

    public void setState(int state) {
        this.currentState = state;
    }

    @Override
    public int getState() {
        return currentState;
    }
}