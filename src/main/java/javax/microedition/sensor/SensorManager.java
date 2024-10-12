/*
 * Copyright 2024 Wafer EX
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

package javax.microedition.sensor;

import java.util.HashMap;

public final class SensorManager {
    private static final HashMap<SensorListener, SensorInfo> sensorsWithInfo = new HashMap<>();
    private static final HashMap<SensorListener, String> sensorsWithQuantity = new HashMap<>();

    public static void addSensorListener(SensorListener listener, SensorInfo info) {
        if (listener == null || info == null) {
            throw new NullPointerException();
        }
        if (!sensorsWithInfo.containsKey(listener) && !sensorsWithQuantity.containsKey(listener)) {
            sensorsWithInfo.put(listener, info);
        }
    }

    public static void addSensorListener(SensorListener listener, String quantity) {
        if (listener == null || quantity == null) {
            throw new NullPointerException();
        }
        if (!sensorsWithQuantity.containsKey(listener) && !sensorsWithInfo.containsKey(listener)) {
            sensorsWithQuantity.put(listener, quantity);
        }
    }

    // TODO: write method logic
    public static SensorInfo[] findSensors(String quantity, String contextType) {
        return new SensorInfo[]{};
    }

    // TODO: write method logic
    public static SensorInfo[] findSensors(String url) {
        if (url == null) {
            throw new NullPointerException();
        }
        return new SensorInfo[]{};
    }

    public static void removeSensorListener(SensorListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        sensorsWithInfo.remove(listener);
        sensorsWithQuantity.remove(listener);
    }
}