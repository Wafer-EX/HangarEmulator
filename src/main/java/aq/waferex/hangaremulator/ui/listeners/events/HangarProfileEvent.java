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

import aq.waferex.hangaremulator.profiles.HangarProfile;

import java.util.EventObject;

public class HangarProfileEvent extends EventObject {
    public static final int MIDLET_KEYCODES_CHANGED = 0;
    public static final int SCALING_MODE_CHANGED = 1;
    public static final int GRAPHICS_ENGINE_CHANGED = 2;
    public static final int RESOLUTION_CHANGED = 3;
    public static final int FRAME_RATE_CHANGED = 4;
    public static final int CANVAS_CLEARING_CHANGED = 5;
    public static final int ANTI_ALIASING_CHANGED = 6;
    public static final int WINDOW_RESIZING_CHANGED = 7;
    public static final int SOUNDBANK_CHANGED = 8;

    private final int state;
    private final Object value;

    public HangarProfileEvent(HangarProfile source, int state, Object value) {
        super(source);
        this.state = state;
        this.value = value;
    }

    public int getStateChange() {
        return state;
    }

    public Object getValue() {
        return value;
    }
}