package things;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;

public class DataRecorder implements RecordEnumeration {
    @Override
    public int numRecords() {
        return 0;
    }

    @Override
    public byte[] nextRecord() {
        return new byte[0];
    }

    @Override
    public void reset() { }

    @Override
    public int nextRecordId() throws RecordStoreException {
        return 0;
    }

    @Override
    public void destroy() { }
}