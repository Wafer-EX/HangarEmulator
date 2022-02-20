package javax.microedition.rms;

public interface RecordEnumeration {
    public int numRecords();

    public byte[] nextRecord() throws InvalidRecordIDException, RecordStoreNotOpenException, RecordStoreException;

    public void reset();

    public int nextRecordId() throws RecordStoreException;

    public void destroy();
}