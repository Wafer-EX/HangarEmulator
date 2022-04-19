package things.implementations;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordEnumerator implements RecordEnumeration, Serializable {
    public List<byte[]> records;
    private int currentRecord = 0;
    private boolean isDestroyed = false;

    public RecordEnumerator() {
        records = new ArrayList();
    }

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
        } catch (InvalidRecordIDException exception) {
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