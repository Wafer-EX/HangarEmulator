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

import javax.microedition.io.Connection;
import java.io.IOException;

public interface SensorConnection extends Connection {
    static final int STATE_OPENED = 1;
    static final int STATE_LISTENING = 2;
    static final int STATE_CLOSED = 4;

    Channel getChannel(ChannelInfo channelInfo);

    Data[] getData(int bufferSize) throws IOException;

    Data[] getData(int bufferSize, long bufferingPeriod, boolean isTimestampIncluded, boolean isUncertaintyIncluded, boolean isValidityIncluded) throws IOException;

    int[] getErrorCodes();

    java.lang.String getErrorText(int errorCode);

    SensorInfo getSensorInfo();

    int getState();

    void removeDataListener();

    void setDataListener(DataListener listener, int bufferSize);

    void setDataListener(DataListener listener, int bufferSize, long bufferingPeriod, boolean isTimestampIncluded, boolean isUncertaintyIncluded, boolean isValidityIncluded);
}