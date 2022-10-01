/*
 * Copyright 2022 Kirill Lomakin
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

package things;

import com.nokia.mid.ui.FullCanvas;

import javax.microedition.lcdui.Canvas;
import java.awt.event.KeyEvent;

public class HangarKeyCodes {
    public static final HangarKeyCodes MIDLET_KEYCODES_DEFAULT = new HangarKeyCodes();
    public static final HangarKeyCodes MIDLET_KEYCODES_NOKIA = new HangarKeyCodes(
            FullCanvas.KEY_UP_ARROW, FullCanvas.KEY_DOWN_ARROW, FullCanvas.KEY_LEFT_ARROW, FullCanvas.KEY_RIGHT_ARROW, FullCanvas.KEY_SOFTKEY3,
            FullCanvas.KEY_SOFTKEY1, FullCanvas.KEY_SOFTKEY2, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null
    );
    public static final HangarKeyCodes AWT_KEYCODES_DEFAULT = new HangarKeyCodes(
            KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER,
            KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
            KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD4,
            KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9,
            KeyEvent.VK_Q, KeyEvent.VK_W
    );

    private int up = Canvas.UP;
    private int down = Canvas.DOWN;
    private int left = Canvas.LEFT;
    private int right = Canvas.RIGHT;
    private int fire = Canvas.FIRE;
    private int a = Canvas.GAME_A;
    private int b = Canvas.GAME_B;
    private int c = Canvas.GAME_C;
    private int d = Canvas.GAME_D;
    private int num0 = Canvas.KEY_NUM0;
    private int num1 = Canvas.KEY_NUM1;
    private int num2 = Canvas.KEY_NUM2;
    private int num3 = Canvas.KEY_NUM3;
    private int num4 = Canvas.KEY_NUM4;
    private int num5 = Canvas.KEY_NUM5;
    private int num6 = Canvas.KEY_NUM6;
    private int num7 = Canvas.KEY_NUM7;
    private int num8 = Canvas.KEY_NUM8;
    private int num9 = Canvas.KEY_NUM9;
    private int star = Canvas.KEY_STAR;
    private int pound = Canvas.KEY_POUND;

    public HangarKeyCodes() { }

    public HangarKeyCodes(Integer up, Integer down, Integer left, Integer right, Integer fire,
                          Integer a, Integer b, Integer c, Integer d,
                          Integer num0, Integer num1, Integer num2, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, Integer num8, Integer num9,
                          Integer star, Integer pound) {
        this.setUp(up);
        this.setDown(down);
        this.setLeft(left);
        this.setRight(right);
        this.setFire(fire);
        this.setA(a);
        this.setB(b);
        this.setC(c);
        this.setD(d);
        this.setNum0(num0);
        this.setNum1(num1);
        this.setNum2(num2);
        this.setNum3(num3);
        this.setNum4(num4);
        this.setNum5(num5);
        this.setNum6(num6);
        this.setNum7(num7);
        this.setNum8(num8);
        this.setNum9(num9);
        this.setStar(star);
        this.setPound(pound);
    }

    public int getUp() {
        return up;
    }

    public void setUp(Integer keyCode) {
        if (keyCode != null) {
            up = keyCode;
        }
    }

    public int getDown() {
        return down;
    }

    public void setDown(Integer keyCode) {
        if (keyCode != null) {
            down = keyCode;
        }
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(Integer keyCode) {
        if (keyCode != null) {
            left = keyCode;
        }
    }

    public int getRight() {
        return right;
    }

    public void setRight(Integer keyCode) {
        if (keyCode != null) {
            right = keyCode;
        }
    }

    public int getFire() {
        return fire;
    }

    public void setFire(Integer keyCode) {
        if (keyCode != null) {
            fire = keyCode;
        }
    }

    public int getA() {
        return a;
    }

    public void setA(Integer keyCode) {
        if (keyCode != null) {
            a = keyCode;
        }
    }

    public int getB() {
        return b;
    }

    public void setB(Integer keyCode) {
        if (keyCode != null) {
            b = keyCode;
        }
    }

    public int getC() {
        return c;
    }

    public void setC(Integer keyCode) {
        if (keyCode != null) {
            c = keyCode;
        }
    }

    public int getD() {
        return d;
    }

    public void setD(Integer keyCode) {
        if (keyCode != null) {
            d = keyCode;
        }
    }

    public int getNum0() {
        return num0;
    }

    public void setNum0(Integer keyCode) {
        if (keyCode != null) {
            num0 = keyCode;
        }
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(Integer keyCode) {
        if (keyCode != null) {
            num1 = keyCode;
        }
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(Integer keyCode) {
        if (keyCode != null) {
            num2 = keyCode;
        }
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(Integer keyCode) {
        if (keyCode != null) {
            num3 = keyCode;
        }
    }

    public int getNum4() {
        return num4;
    }

    public void setNum4(Integer keyCode) {
        if (keyCode != null) {
            num4 = keyCode;
        }
    }

    public int getNum5() {
        return num5;
    }

    public void setNum5(Integer keyCode) {
        if (keyCode != null) {
            num5 = keyCode;
        }
    }

    public int getNum6() {
        return num6;
    }

    public void setNum6(Integer keyCode) {
        if (keyCode != null) {
            num6 = keyCode;
        }
    }

    public int getNum7() {
        return num7;
    }

    public void setNum7(Integer keyCode) {
        if (keyCode != null) {
            num7 = keyCode;
        }
    }

    public int getNum8() {
        return num8;
    }

    public void setNum8(Integer keyCode) {
        if (keyCode != null) {
            num8 = keyCode;
        }
    }

    public int getNum9() {
        return num9;
    }

    public void setNum9(Integer keyCode) {
        if (keyCode != null) {
            num9 = keyCode;
        }
    }

    public int getStar() {
        return star;
    }

    public void setStar(Integer keyCode) {
        if (keyCode != null) {
            star = keyCode;
        }
    }

    public int getPound() {
        return pound;
    }

    public void setPound(Integer keyCode) {
        if (keyCode != null) {
            pound = keyCode;
        }
    }
}