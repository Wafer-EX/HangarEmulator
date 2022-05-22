package things;

import things.enums.Keyboards;

public class HangarState {
    private static Keyboards selectedKeyboard = Keyboards.Default;

    public static Keyboards getKeyboard() {
        return selectedKeyboard;
    }

    public static void setKeyboard(Keyboards keyboard) {
        selectedKeyboard = keyboard;
        var keyListeners = HangarPanel.getInstance().getKeyListeners();
        if (keyListeners.length > 0) {
            for (var keyListener : keyListeners) {
                if (keyListener instanceof HangarKeyListener) {
                    var hangarKeyListener = (HangarKeyListener) keyListener;
                    hangarKeyListener.getPressedKeys().clear();
                }
            }
        }
    }
}