/*
 * Copyright 2022-2024 Wafer EX
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

package things.utils;

import things.HangarKeyCodes;

public final class KeyUtils {
    public static int convertKeyCode(int keyCode, HangarKeyCodes from, HangarKeyCodes to) throws IllegalArgumentException {
        if (keyCode == from.getUp()) {
            return to.getUp();
        }
        else if (keyCode == from.getDown()) {
            return to.getDown();
        }
        else if (keyCode == from.getLeft()) {
            return to.getLeft();
        }
        else if (keyCode == from.getRight()) {
            return to.getRight();
        }
        else if (keyCode == from.getFire()) {
            return to.getFire();
        }
        else if (keyCode == from.getA()) {
            return to.getA();
        }
        else if (keyCode == from.getB()) {
            return to.getB();
        }
        else if (keyCode == from.getC()) {
            return to.getC();
        }
        else if (keyCode == from.getD()) {
            return to.getD();
        }
        else if (keyCode == from.getNum0()) {
            return to.getNum0();
        }
        else if (keyCode == from.getNum1()) {
            return to.getNum1();
        }
        else if (keyCode == from.getNum2()) {
            return to.getNum2();
        }
        else if (keyCode == from.getNum3()) {
            return to.getNum3();
        }
        else if (keyCode == from.getNum4()) {
            return to.getNum4();
        }
        else if (keyCode == from.getNum5()) {
            return to.getNum5();
        }
        else if (keyCode == from.getNum6()) {
            return to.getNum6();
        }
        else if (keyCode == from.getNum7()) {
            return to.getNum7();
        }
        else if (keyCode == from.getNum8()) {
            return to.getNum8();
        }
        else if (keyCode == from.getNum9()) {
            return to.getNum9();
        }
        else if (keyCode == from.getStar()) {
            return to.getStar();
        }
        else if (keyCode == from.getPound()) {
            return to.getPound();
        }
        return 0;
    }
}