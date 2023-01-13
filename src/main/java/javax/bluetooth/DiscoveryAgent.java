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

package javax.bluetooth;

public class DiscoveryAgent {
    public static final int NOT_DISCOVERABLE = 0x00;

    public static final int GIAC = 0x9E8B33;

    public static final int LIAC = 0x9E8B00;

    public static final int CACHED = 0x00;

    public static final int PREKNOWN = 0x01;

    public RemoteDevice[] retrieveDevices(int option) {
        // TODO: write method logic
        return null;
    }

    public boolean startInquiry(int accessCode, DiscoveryListener listener) throws BluetoothStateException {
        // TODO: write method logic
        return false;
    }

    public boolean cancelInquiry(DiscoveryListener listener) {
        // TODO: write method logic
        return false;
    }

    public int searchServices(int[] attrSet, UUID[] uuidSet, RemoteDevice btDev, DiscoveryListener discListener) throws BluetoothStateException {
        // TODO: write method logic
        return 0;
    }

    public boolean cancelServiceSearch(int transID) {
        // TODO: write method logic
        return false;
    }

    public String selectService(UUID uuid, int security, boolean master) throws BluetoothStateException {
        // TODO: write method logic
        return null;
    }
}