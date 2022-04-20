package javax.microedition.rms;

import things.implementations.RecordEnumerator;

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;

import static things.utils.RecordStoreUtils.*;

public class RecordStore implements Serializable {
    public static final int AUTHMODE_PRIVATE = 0;
    public static final int AUTHMODE_ANY = 1;

    private static final Hashtable<String, RecordStore> openedRecords = new Hashtable();
    private RecordEnumerator recordEnumerator;
    private File recordEnumeratorPath;
    private String name;
    private boolean isOpened = true;

    private RecordStore(String name, File recordEnumeratorPath, RecordEnumerator recordEnumerator) {
        this.name = name;
        this.recordEnumeratorPath = recordEnumeratorPath;
        this.recordEnumerator = recordEnumerator;
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreException, IllegalArgumentException {
        if (recordStoreName == null) {
            throw new IllegalArgumentException();
        }
        if (openedRecords.containsKey(recordStoreName)) {
            return openedRecords.get(recordStoreName);
        }

        RecordStore recordStore;
        var file = new File(getRecordsPath() + recordStoreName);

        if (file.exists()) {
            var recordEnumerator = readRecordEnumerator(file);
            recordStore = new RecordStore(recordStoreName, file, recordEnumerator);
        }
        else if (createIfNecessary) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                recordStore = new RecordStore(recordStoreName, file, new RecordEnumerator());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            throw new RecordStoreNotFoundException();
        }
        openedRecords.put(recordStoreName, recordStore);
        writeRecordEnumerator(recordStore.recordEnumerator, file);
        return recordStore;
    }

    public String getName() throws RecordStoreNotOpenException {
        return name;
    }

    public int getNumRecords() throws RecordStoreNotOpenException {
        return recordEnumerator.numRecords();
    }

    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) throws RecordStoreNotOpenException {
        // TODO: write logic for filter and comparator
        writeRecordEnumerator(recordEnumerator, recordEnumeratorPath);
        return recordEnumerator;
    }

    public int addRecord(byte[] arr, int offset, int numBytes) throws RecordStoreException {
        if (arr == null) {
            arr = new byte[0];
        }
        byte[] subArray = Arrays.copyOfRange(arr, offset, offset + numBytes);
        recordEnumerator.records.add(subArray);
        writeRecordEnumerator(recordEnumerator, recordEnumeratorPath);
        return recordEnumerator.numRecords();
    }

    public void closeRecordStore() throws RecordStoreNotOpenException, RecordStoreException {
        // TODO: write method logic?
        if (!isOpened) {
            throw new RecordStoreNotOpenException();
        }
        writeRecordEnumerator(recordEnumerator, recordEnumeratorPath);
    }

    public byte[] getRecord(int recordId) {
        return recordEnumerator.records.get(recordId - 1);
    }

    public void setRecord(int recordId, byte[] arr, int offset, int numBytes) throws RecordStoreException {
        if (recordEnumerator.records.size() < recordId) {
            throw new RecordStoreException();
        }
        byte[] subArray = Arrays.copyOfRange(arr, offset, offset + numBytes);
        recordEnumerator.records.set(recordId - 1, subArray);
    }

    public static String[] listRecordStores() {
        File[] appFiles = new File(getRecordsPath()).listFiles();
        if (appFiles.length == 0) {
            return null;
        }
        else {
            String[] appFileNames = new String[appFiles.length];
            for (int i = 0; i < appFiles.length; i++) {
                appFileNames[i] = appFiles[i].getName();
            }
            return appFileNames;
        }
    }
}