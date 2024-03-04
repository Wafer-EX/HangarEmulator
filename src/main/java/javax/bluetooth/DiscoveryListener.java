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

public interface DiscoveryListener {
    static final int INQUIRY_COMPLETED = 0x00;
    static final int INQUIRY_TERMINATED = 0x05;
    static final int INQUIRY_ERROR = 0x07;
    static final int SERVICE_SEARCH_COMPLETED = 0x01;
    static final int SERVICE_SEARCH_TERMINATED = 0x02;
    static final int SERVICE_SEARCH_ERROR = 0x03;
    static final int SERVICE_SEARCH_NO_RECORDS = 0x04;
    static final int SERVICE_SEARCH_DEVICE_NOT_REACHABLE = 0x06;

    void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod);

    void servicesDiscovered(int transID, ServiceRecord[] servRecord);

    void serviceSearchCompleted(int transID, int respCode);

    void inquiryCompleted(int discType);
}