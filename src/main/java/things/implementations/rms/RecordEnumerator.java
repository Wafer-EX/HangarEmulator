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

package things.implementations.rms;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordEnumerator implements RecordEnumeration, Serializable {
    public List<byte[]> records = new ArrayList<>();
    private int currentRecord = 1;
    private boolean isDestroyed = false;

    public RecordEnumerator() { }

    @Override
    public int numRecords() {
        if (isDestroyed) {
            throw new IllegalStateException();
        }
        return records.size();
    }

    @Override
    public byte[] nextRecord() throws InvalidRecordIDException, RecordStoreNotOpenException, RecordStoreException {
        if (isDestroyed) {
            throw new IllegalStateException();
        }
        try {
            int nextId = nextRecordId();
            byte[] nextRecord = records.get(nextId);
            return nextRecord.clone();
        }
        catch (InvalidRecordIDException exception) {
            throw new InvalidRecordIDException();
        }
    }

    @Override
    public int nextRecordId() throws InvalidRecordIDException {
        if (isDestroyed) {
            throw new IllegalStateException();
        }
        if (currentRecord + 1 > records.size()) {
            throw new InvalidRecordIDException();
        }
        currentRecord += 1;
        return currentRecord;
    }

    @Override
    public boolean hasNextElement() {
        try {
            return records.get(currentRecord + 1) != null;
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean hasPreviousElement() {
        try {
            return records.get(currentRecord - 1) != null;
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public void reset() {
        if (isDestroyed) {
            throw new IllegalStateException();
        }
        currentRecord = 0;
    }

    @Override
    public void destroy() {
        if (isDestroyed) {
            throw new IllegalStateException();
        }
        records.clear();
        currentRecord = 0;
        isDestroyed = true;
    }
}