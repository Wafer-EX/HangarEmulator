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

package aq.waferex.hangaremulator.ui.listeners.events;

import javax.microedition.lcdui.Displayable;
import java.util.EventObject;

public class HangarDisplayableEvent extends EventObject {
    public static final int SET = 0;
    public static final int UNSET = 1;

    private final int state;

    public HangarDisplayableEvent(Displayable source, int state) {
        super(source);
        this.state = state;
    }

    public int getStateChange() {
        return state;
    }
}