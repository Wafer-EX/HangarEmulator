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

import java.io.IOException;

public class BluetoothConnectionException extends IOException {
    public static final int UNKNOWN_PSM = 0x0001;
    public static final int SECURITY_BLOCK = 0x0002;
    public static final int NO_RESOURCES = 0x0003;
    public static final int FAILED_NOINFO = 0x0004;
    public static final int TIMEOUT = 0x0005;
    public static final int UNACCEPTABLE_PARAMS = 0x0006;

    private final int status;

    public BluetoothConnectionException(int error) {
        super();
        this.status = error;
    }

    public BluetoothConnectionException(int error, String message) {
        super(message);
        this.status = error;
    }

    public int getStatus() {
        return status;
    }
}