package things;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordEnumerator implements RecordEnumeration, Serializable {
    private boolean isDestroyed = false;

    public final List<byte[]> data;
    public int enumerationIndex = 0;

    public RecordEnumerator() {
        data = new ArrayList();
    }

    @Override
    public int numRecords() {
        return data.size();
    }

    @Override
    public byte[] nextRecord() throws InvalidRecordIDException, RecordStoreNotOpenException, RecordStoreException {
        if (enumerationIndex + 1 >= data.size()) {
            throw new InvalidRecordIDException();
        }
        enumerationIndex += 1;
        var nextData = data.get(enumerationIndex);
        return nextData.clone();
    }

    @Override
    public int nextRecordId() throws InvalidRecordIDException {
        if (enumerationIndex + 1 >= data.size()) {
            throw new InvalidRecordIDException();
        }
        enumerationIndex += 1;
        return enumerationIndex;
    }

    @Override
    public void reset() {
        enumerationIndex = 0;
    }

    @Override
    public void destroy() {
        // TODO: add this to all methods
        if (isDestroyed) {
            throw new IllegalStateException();
        }
        data.clear();
        enumerationIndex = 0;
        isDestroyed = true;
    }
}