package javax.microedition.rms;

import things.DataRecorder;

import java.util.Hashtable;

public class RecordStore {
    private static final Hashtable<String, RecordStore> openedRecords = new Hashtable();

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException {
        if (!openedRecords.containsKey(recordStoreName)) {
            openedRecords.put(recordStoreName, createRecordStore(recordStoreName, createIfNecessary));
        }
        return openedRecords.get(recordStoreName);
    }

    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) throws RecordStoreNotOpenException {
        return new DataRecorder();
    }

    private static RecordStore createRecordStore(String name, boolean createIfNecessary) throws RecordStoreException {
        return new RecordStore();
    }

    public int addRecord(byte[] arr, int offset, int numBytes) throws RecordStoreException {
        return 0;
    }

    public void closeRecordStore() throws RecordStoreException { }

    public void setRecord(int recordId, byte[] arr, int offset, int numBytes) throws RecordStoreException { }

    public static String[] listRecordStores() {
        return null;
    }
}