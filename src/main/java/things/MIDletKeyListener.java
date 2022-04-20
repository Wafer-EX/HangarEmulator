package things;

import things.utils.KeyCodeConverter;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.game.GameCanvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MIDletKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        var displayable = CanvasPanel.getDisplayable();
        if (displayable instanceof Canvas) {
            int convertedKeyCode = KeyCodeConverter.awtToDefault(e.getKeyCode());
            if (displayable instanceof GameCanvas) {
                convertedKeyCode = KeyCodeConverter.defaultToNokia(convertedKeyCode);
            }
            var canvas = (Canvas)displayable;
            canvas.keyPressed(convertedKeyCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        var displayable = CanvasPanel.getDisplayable();
        if (displayable instanceof Canvas) {
            int convertedKeyCode = KeyCodeConverter.awtToDefault(e.getKeyCode());
            if (displayable instanceof GameCanvas) {
                convertedKeyCode = KeyCodeConverter.defaultToNokia(convertedKeyCode);
            }

            var canvas = (Canvas)displayable;
            canvas.keyReleased(convertedKeyCode);
        }
    }
}