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

package things.profiles;

import things.ui.listeners.HangarProfileManagerListener;
import things.ui.listeners.events.HangarProfileManagerEvent;

import java.util.ArrayList;
import java.util.Objects;

public class HangarProfileManager {
    private final ArrayList<HangarProfileManagerListener> profileManagerListeners = new ArrayList<>();
    private final ArrayList<HangarProfile> profiles;
    private  HangarProfile defaultProfile;
    private int selectedIndex = 0;

    public HangarProfileManager(HangarProfile defaultProfile) {
        this.defaultProfile = Objects.requireNonNullElseGet(defaultProfile, HangarProfile::new);
        this.profiles = new ArrayList<>();
    }

    public HangarProfile getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(HangarProfile profile) {
        this.defaultProfile = profile;
        for (var profileManagerListener : profileManagerListeners) {
            var event = new HangarProfileManagerEvent(this, HangarProfileManagerEvent.DEFAULT_PROFILE_CHANGED, profile);
            profileManagerListener.profileManagerStateChanged(event);
        }
    }

    public int size(boolean includeDefault) {
        if (includeDefault) {
            return profiles.size() + 1;
        }
        return profiles.size();
    }

    public int next() throws IllegalStateException {
        notifyUnset();
        selectedIndex++;
        if (selectedIndex > profiles.size()) {
            selectedIndex = 0;
        }

        notifySet();
        return selectedIndex;
    }

    public int previous() throws IllegalStateException {
        notifyUnset();
        selectedIndex--;
        if (selectedIndex < 0) {
            selectedIndex = profiles.size() - 1;
        }

        notifySet();
        return selectedIndex;
    }

    public HangarProfile getCurrent() {
        if (selectedIndex == 0) {
            return defaultProfile;
        }
        return profiles.get(selectedIndex - 1);
    }

    public void add(HangarProfile profile) {
        if (profile == null) {
            throw new NullPointerException();
        }
        this.profiles.add(profile);
    }

    public void addProfileManagerListener(HangarProfileManagerListener listener) {
        this.profileManagerListeners.add(listener);
    }

    private void notifySet() {
        for (var profileManagerListener : profileManagerListeners) {
            var event = new HangarProfileManagerEvent(this, HangarProfileManagerEvent.PROFILE_SET, getCurrent());
            profileManagerListener.profileManagerStateChanged(event);
        }
    }

    private void notifyUnset() {
        for (var profileManagerListener : profileManagerListeners) {
            var event = new HangarProfileManagerEvent(this, HangarProfileManagerEvent.PROFILE_UNSET, getCurrent());
            profileManagerListener.profileManagerStateChanged(event);
        }
    }
}