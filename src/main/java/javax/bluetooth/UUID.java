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

public class UUID {
    protected final long value;

    public UUID(long uuidValue) {
        if (uuidValue < 0) {
            throw new IllegalArgumentException();
        }
        this.value = uuidValue;
    }

    public UUID(String uuidValue, boolean shortUUID) {
        if (uuidValue == null) {
            throw new NullPointerException();
        }
        if (uuidValue.length() == 0 || (shortUUID && uuidValue.length() > 8) || uuidValue.length() > 32) {
            throw new IllegalArgumentException();
        }
        // TODO: fix it
        this.value = Long.decode(uuidValue);
    }

    @Override
    public String toString() {
        // TODO: check it
        return Long.toHexString(value);
    }

    @Override
    public boolean equals(Object value) {
        if (value instanceof UUID uuid) {
            return this.value == uuid.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // TODO: check it
        return super.hashCode();
    }
}