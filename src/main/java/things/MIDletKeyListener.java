package things;

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
            int convertedKeyCode = 0;
            if (displayable instanceof GameCanvas) {
                convertedKeyCode = convertGameKeyCode(e.getKeyCode());
            }
            else {
                convertedKeyCode = convertKeyCode(e.getKeyCode());
            }

            var canvas = (Canvas)displayable;
            canvas.keyPressed(convertedKeyCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int convertedKeyCode = convertKeyCode(e.getKeyCode());
        var displayable = CanvasPanel.getDisplayable();

        if (displayable instanceof Canvas) {
            var canvas = (Canvas)displayable;
            canvas.keyReleased(convertedKeyCode);
        }
    }

    private static int convertKeyCode(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP: return Canvas.UP;
            case KeyEvent.VK_DOWN: return Canvas.DOWN;
            case KeyEvent.VK_LEFT: return Canvas.LEFT;
            case KeyEvent.VK_RIGHT: return Canvas.RIGHT;
            case KeyEvent.VK_ENTER: return Canvas.FIRE;
            default: return 0;
        }
    }

    private static int convertGameKeyCode(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP: return GameCanvas.KEY_NUM2;
            case KeyEvent.VK_DOWN: return GameCanvas.KEY_NUM8;
            case KeyEvent.VK_LEFT: return GameCanvas.KEY_NUM4;
            case KeyEvent.VK_RIGHT: return GameCanvas.KEY_NUM6;
            case KeyEvent.VK_ENTER: return GameCanvas.KEY_NUM5;
            default: return convertKeyCode(keyCode);
        }
    }
}