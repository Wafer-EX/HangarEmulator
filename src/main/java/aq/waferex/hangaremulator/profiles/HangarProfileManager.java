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

package aq.waferex.hangaremulator.profiles;

import aq.waferex.hangaremulator.ui.listeners.HangarProfileManagerListener;
import aq.waferex.hangaremulator.ui.listeners.events.HangarProfileManagerEvent;

import java.util.ArrayList;
import java.util.Objects;

public class HangarProfileManager {
    private final ArrayList<HangarProfileManagerListener> profileManagerListeners = new ArrayList<>();
    private final ArrayList<HangarProfile> profileList;
    private final HangarProfile defaultProfile;
    private HangarProfile currentProfile;

    public HangarProfileManager(HangarProfile defaultProfile) {
        this.profileList = new ArrayList<>();
        this.defaultProfile = Objects.requireNonNullElseGet(defaultProfile, HangarProfile::new);
        this.currentProfile = new HangarProfile();
    }

    public ArrayList<HangarProfile> getProfileList() {
        return profileList;
    }

    public HangarProfile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(HangarProfile profile) {
        if (profile == null) {
            throw new NullPointerException();
        }

        for (var profileManagerListener : profileManagerListeners) {
            var setEvent = new HangarProfileManagerEvent(this, HangarProfileManagerEvent.PROFILE_SET, profile);
            var unsetEvent = new HangarProfileManagerEvent(this, HangarProfileManagerEvent.PROFILE_UNSET, currentProfile);
            profileManagerListener.profileManagerStateChanged(setEvent);
            profileManagerListener.profileManagerStateChanged(unsetEvent);
        }

        this.currentProfile = profile;
    }

    public void addProfileManagerListener(HangarProfileManagerListener listener) {
        this.profileManagerListeners.add(listener);
    }
}