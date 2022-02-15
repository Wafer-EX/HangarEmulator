package things;

import javax.microedition.lcdui.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MIDletKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int convertedKeyCode = convertKeyCode(e.getKeyCode());
        CanvasPanel.getCanvas().keyPressed(convertedKeyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int convertedKeyCode = convertKeyCode(e.getKeyCode());
        CanvasPanel.getCanvas().keyReleased(convertedKeyCode);
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
}