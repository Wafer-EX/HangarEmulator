package javax.microedition.rms;

import things.MIDletResources;
import things.implementations.RecordEnumerator;

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;

public class RecordStore {
    public static final int AUTHMODE_PRIVATE = 0;
    public static final int AUTHMODE_ANY = 1;

    private static String recordsPath = String.format("records/%s/", MIDletResources.getMIDletName());
    private File file;
    private static final Hashtable<String, RecordStore> openedRecords = new Hashtable();
    private static RecordEnumerator recordEnumerator;

    private RecordStore(File file, RecordEnumerator recordEnumerator) {
        this.file = file;
        this.recordEnumerator = recordEnumerator;
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreException, IllegalArgumentException {
        if (openedRecords.containsKey(recordStoreName)) {
            return openedRecords.get(recordStoreName);
        }

        var file = new File(recordsPath + recordStoreName);
        RecordStore recordStore = null;

        try {
            if (file.exists()) {
                var fileInputStream = new FileInputStream(file);
                var objectInputStream = new ObjectInputStream(fileInputStream);
                var recordEnumerator = objectInputStream.readObject();
                recordStore = new RecordStore(file, (RecordEnumerator) recordEnumerator);
                openedRecords.put(recordStoreName, recordStore);
            }
            else if (createIfNecessary) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                recordStore = new RecordStore(file, new RecordEnumerator());
                openedRecords.put(recordStoreName, recordStore);
                recordStore.writeRecordEnumerator();
            }
            else throw new RecordStoreNotFoundException();
        }
        catch (IOException | ClassNotFoundException exception) {
            throw new RecordStoreException();
        }
        catch (RecordStoreNotFoundException exception) {
            throw exception;
        }
        return recordStore;
    }

    public int getNumRecords() throws RecordStoreNotOpenException {
        return recordEnumerator.numRecords();
    }

    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) throws RecordStoreNotOpenException {
        if (filter == null && comparator == null) {
            return recordEnumerator;
        }
        else {
            // TODO: write logic for filter and comparator
            return recordEnumerator;
        }
    }

    public int addRecord(byte[] arr, int offset, int numBytes) throws RecordStoreException {
        if (arr == null) {
            arr = new byte[0];
        }
        byte[] subArray = Arrays.copyOfRange(arr, offset, offset + numBytes);
        recordEnumerator.records.add(subArray);
        writeRecordEnumerator();
        return recordEnumerator.records.size() - 1;
    }

    public void closeRecordStore() throws RecordStoreException {
        // TODO: write method logic
    }

    public void setRecord(int recordId, byte[] arr, int offset, int numBytes) throws RecordStoreException {
        if (recordEnumerator.records.size() <= recordId) {
            throw new RecordStoreException();
        }
        byte[] subArray = Arrays.copyOfRange(arr, offset, offset + numBytes);
        recordEnumerator.records.set(recordId, subArray);
        writeRecordEnumerator();
    }

    public static String[] listRecordStores() {
        File[] appFiles = new File(recordsPath).listFiles();
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

    private void writeRecordEnumerator() {
        try {
            var fileOutputStream = new FileOutputStream(file);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recordEnumerator);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}