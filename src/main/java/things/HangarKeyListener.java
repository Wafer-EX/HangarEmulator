package things;

import things.enums.Keyboards;
import things.utils.KeyCodeConverter;

import javax.microedition.lcdui.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class HangarKeyListener implements KeyListener {
    private final HashMap<Integer, Boolean> pressedKeys = new HashMap<>();

    public HashMap<Integer, Boolean> getPressedKeys() {
        return pressedKeys;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        var displayable = HangarPanel.getDisplayable();
        if (displayable instanceof Canvas canvas) {
            int convertedKeyCode = KeyCodeConverter.awtToDefault(e.getKeyCode());
            if (HangarState.getKeyboard() == Keyboards.Nokia) {
                convertedKeyCode = KeyCodeConverter.defaultToNokia(convertedKeyCode);
            }

            if (pressedKeys.containsKey(convertedKeyCode)) {
                if (!pressedKeys.get(convertedKeyCode)) {
                    pressedKeys.put(convertedKeyCode, true);
                }
            }
            else {
                pressedKeys.put(convertedKeyCode, false);
            }

            for (int key : pressedKeys.keySet()) {
                if (pressedKeys.get(key)) {
                    canvas.keyRepeated(convertedKeyCode);
                }
                else {
                    canvas.keyPressed(convertedKeyCode);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        var displayable = HangarPanel.getDisplayable();
        if (displayable instanceof Canvas canvas) {
            int convertedKeyCode = KeyCodeConverter.awtToDefault(e.getKeyCode());
            if (HangarState.getKeyboard() == Keyboards.Nokia) {
                convertedKeyCode = KeyCodeConverter.defaultToNokia(convertedKeyCode);
            }
            pressedKeys.remove(convertedKeyCode);
            canvas.keyReleased(convertedKeyCode);
        }
    }
}