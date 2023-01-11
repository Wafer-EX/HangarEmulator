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

package things.ui.listeners.events;

import things.profiles.HangarProfile;
import things.profiles.HangarProfileManager;

import java.util.EventObject;

public class HangarProfileManagerEvent extends EventObject {
    public static final int DEFAULT_PROFILE_CHANGED = 0;
    public static final int PROFILE_SET = 1;
    public static final int PROFILE_UNSET = 2;

    private final HangarProfileManager source;
    private final int state;
    private final HangarProfile profile;

    public HangarProfileManagerEvent(HangarProfileManager source, int state, HangarProfile profile) {
        super(source);
        this.source = source;
        this.state = state;
        this.profile = profile;
    }

    @Override
    public Object getSource() {
        return source;
    }

    public int getStateChange() {
        return state;
    }

    public HangarProfile getProfile() {
        return profile;
    }
}