package javax.microedition.rms;

public interface RecordEnumeration {
    public int numRecords();

    public byte[] nextRecord();

    public void reset();

    public int nextRecordId() throws RecordStoreException;

    public void destroy();
}
