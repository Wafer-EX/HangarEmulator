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

public interface SensorInfo {
    static final int CONN_EMBEDDED = 1;
    static final int CONN_REMOTE = 2;
    static final int CONN_SHORT_RANGE_WIRELESS = 4;
    static final int CONN_WIRED = 8;
    static final String CONTEXT_TYPE_AMBIENT = "ambient";
    static final String CONTEXT_TYPE_VEHICLE = "vehicle";
    static final String CONTEXT_TYPE_DEVICE = "device";
    static final String CONTEXT_TYPE_USER = "user";
    static final String PROP_IS_CONTROLLABLE = "controllable";
    static final String PROP_IS_REPORTING_ERRORS = "errorsReported";
    static final String PROP_LATITUDE = "latitude";
    static final String PROP_LOCATION = "location";
    static final String PROP_LONGITUDE = "longitude";
    static final String PROP_MAX_RATE = "maxSamplingRate";
    static final String PROP_PERMISSION = "permission";
    static final String PROP_VENDOR = "vendor";
    static final String PROP_VERSION = "version";

    ChannelInfo[] getChannelInfos();

    int getConnectionType();

    String getContextType();

    String getDescription();

    int getMaxBufferSize();

    String getModel();

    String[] getPropertyNames();

    Object getProperty(String name);

    String getQuantity();

    String getUrl();

    boolean isAvailable();

    boolean isAvailabilityPushSupported();

    boolean isConditionPushSupported();
}