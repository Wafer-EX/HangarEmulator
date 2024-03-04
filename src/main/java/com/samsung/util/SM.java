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

package com.samsung.util;

public class SM {
    private String destAddress;
    private String callbackAddress;
    private String data;

    public SM() { }

    public SM(String dest, String callback, String textMessage) throws IllegalArgumentException {
        this.setDestAddress(dest);
        this.setCallbackAddress(callback);
        this.setData(textMessage);
    }

    public void setDestAddress(String address) throws IllegalArgumentException {
        // TODO: add phone number validation
        destAddress = address;
    }

    public void setCallbackAddress(String address) {
        callbackAddress = address;
    }

    public void setData(String textMessage) throws IllegalArgumentException {
        if (textMessage.length() > 80) {
            throw new IllegalArgumentException();
        }
        data = textMessage;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public String getCallbackAddress() {
        return callbackAddress;
    }

    public String getData() {
        return data;
    }
}