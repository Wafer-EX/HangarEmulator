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

package javax.bluetooth;

import javax.microedition.io.Connection;

public class LocalDevice {
    public static LocalDevice getLocalDevice() throws BluetoothStateException {
        // TODO: write method logic
        return null;
    }

    public static boolean isPowerOn() {
        // TODO: write method logic
        return false;
    }

    public DiscoveryAgent getDiscoveryAgent() {
        // TODO: write method logic
        return null;
    }

    public String getFriendlyName() {
        // TODO: write method logic
        return null;
    }

    public DeviceClass getDeviceClass() {
        // TODO: write method logic
        return null;
    }

    public boolean setDiscoverable(int mode) throws BluetoothStateException {
        // TODO: write method logic
        return false;
    }

    public static String getProperty(String property) {
        // TODO: write method logic
        return null;
    }

    public int getDiscoverable() {
        // TODO: write method logic
        return 0;
    }

    public String getBluetoothAddress() {
        // TODO: write method logic
        return null;
    }

    public ServiceRecord getRecord(Connection notifier) {
        // TODO: write method logic
        return null;
    }

    public void updateRecord(ServiceRecord srvRecord) throws ServiceRegistrationException {

    }
}