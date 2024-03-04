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

import java.util.Objects;

public class UUID {
    protected final String value;

    public UUID(long uuidValue) {
        this(Long.toHexString(uuidValue), false);
        if (uuidValue < 0) {
            throw new IllegalArgumentException();
        }
    }

    public UUID(String uuidValue, boolean shortUUID) {
        if (uuidValue == null) {
            throw new NullPointerException();
        }
        if (uuidValue.length() == 0 || (shortUUID && uuidValue.length() > 8) || uuidValue.length() > 32) {
            throw new IllegalArgumentException();
        }
        this.value = uuidValue;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object value) {
        if (value instanceof UUID uuid) {
            return Objects.equals(this.value, uuid.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}