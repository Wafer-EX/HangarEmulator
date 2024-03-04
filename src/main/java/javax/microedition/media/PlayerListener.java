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

package javax.microedition.media;

public interface PlayerListener {
    public static final String STARTED = "STARTED";
    public static final String STOPPED = "STOPPED";
    public static final String END_OF_MEDIA = "END_OF_MEDIA";
    public static final String DURATION_UPDATED = "DURATION_UPDATED";
    public static final String DEVICE_UNAVAILABLE = "DEVICE_UNAVAILABLE";
    public static final String DEVICE_AVAILABLE = "DEVICE_AVAILABLE";
    public static final String VOLUME_CHANGED = "VOLUME_CHANGED";
    public static final String ERROR = "ERROR";
    public static final String CLOSED = "CLOSED";

    public void playerUpdate(Player player, String event, Object eventData);
}