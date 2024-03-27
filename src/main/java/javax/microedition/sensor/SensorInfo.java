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
    static final int CONN_EMBEDDED = 0;
    static final int CONN_REMOTE = 0;
    static final int CONN_SHORT_RANGE_WIRELESS = 0;
    static final int CONN_WIRED = 0;
    static final String CONTEXT_TYPE_AMBIENT = "";
    static final String CONTEXT_TYPE_VEHICLE = "";
    static final String CONTEXT_TYPE_DEVICE = "";
    static final String CONTEXT_TYPE_USER = "";
    static final String PROP_IS_CONTROLLABLE = "";
    static final String PROP_IS_REPORTING_ERRORS = "";
    static final String PROP_LATITUDE = "";
    static final String PROP_LOCATION = "";
    static final String PROP_LONGITUDE = "";
    static final String PROP_MAX_RATE = "";
    static final String PROP_PERMISSION = "";
    static final String PROP_VENDOR = "";
    static final String PROP_VERSION = "";

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