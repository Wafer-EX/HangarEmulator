/*
 * Copyright 2022-2023 Kirill Lomakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package things.utils.microedition;

import com.nokia.mid.ui.FullCanvas;

import javax.microedition.lcdui.Canvas;

public final class CanvasUtils {
    public static int keyCodeToGameAction(int keyCode) {
        return switch (keyCode) {
            case Canvas.KEY_NUM1 -> Canvas.GAME_A;
            case Canvas.KEY_NUM2 -> Canvas.UP;
            case Canvas.KEY_NUM3 -> Canvas.GAME_B;
            case Canvas.KEY_NUM4 -> Canvas.LEFT;
            case Canvas.KEY_NUM5 -> Canvas.FIRE;
            case Canvas.KEY_NUM6 -> Canvas.RIGHT;
            case Canvas.KEY_NUM7 -> Canvas.GAME_C;
            case Canvas.KEY_NUM8 -> Canvas.DOWN;
            case Canvas.KEY_NUM9 -> Canvas.GAME_D;

            case FullCanvas.KEY_UP_ARROW -> Canvas.UP;
            case FullCanvas.KEY_LEFT_ARROW -> Canvas.LEFT;
            case FullCanvas.KEY_RIGHT_ARROW -> Canvas.RIGHT;
            case FullCanvas.KEY_DOWN_ARROW -> Canvas.DOWN;
            case FullCanvas.KEY_SOFTKEY1 -> Canvas.GAME_A;
            case FullCanvas.KEY_SOFTKEY2 -> Canvas.GAME_B;
            case FullCanvas.KEY_SOFTKEY3 -> Canvas.FIRE;

            default -> keyCode;
        };
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