package things.utils;

import com.nokia.mid.ui.FullCanvas;

import javax.microedition.lcdui.Canvas;

public final class CanvasUtils {
    public static int keyCodeToGameAction(int keyCode) {
        switch (keyCode) {
            case Canvas.KEY_NUM1: return Canvas.GAME_A;
            case Canvas.KEY_NUM2: return Canvas.UP;
            case Canvas.KEY_NUM3: return Canvas.GAME_B;
            case Canvas.KEY_NUM4: return Canvas.LEFT;
            case Canvas.KEY_NUM5: return Canvas.FIRE;
            case Canvas.KEY_NUM6: return Canvas.RIGHT;
            case Canvas.KEY_NUM7: return Canvas.GAME_C;
            case Canvas.KEY_NUM8: return Canvas.DOWN;
            case Canvas.KEY_NUM9: return Canvas.GAME_D;

            case FullCanvas.KEY_UP_ARROW: return Canvas.UP;
            case FullCanvas.KEY_LEFT_ARROW: return Canvas.LEFT;
            case FullCanvas.KEY_RIGHT_ARROW: return Canvas.RIGHT;
            case FullCanvas.KEY_DOWN_ARROW: return Canvas.DOWN;
            case FullCanvas.KEY_SOFTKEY1: return Canvas.GAME_A;
            case FullCanvas.KEY_SOFTKEY2: return Canvas.GAME_B;
            case FullCanvas.KEY_SOFTKEY3: return Canvas.FIRE;

            default: return keyCode;
        }
    }

    public static int gameActionToKeyCode(int gameAction) {
        switch (gameAction) {
            case Canvas.GAME_A: return FullCanvas.KEY_SOFTKEY1;
            case Canvas.GAME_B: return FullCanvas.KEY_SOFTKEY2;
            case Canvas.UP: return FullCanvas.KEY_UP_ARROW;
            case Canvas.LEFT: return FullCanvas.KEY_LEFT_ARROW;
            case Canvas.FIRE: return FullCanvas.KEY_SOFTKEY3;
            case Canvas.RIGHT: return FullCanvas.KEY_RIGHT_ARROW;
            case Canvas.DOWN: return FullCanvas.KEY_DOWN_ARROW;
            default: return gameAction;
        }
    }
}