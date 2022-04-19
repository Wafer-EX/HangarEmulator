package javax.microedition.rms;

import things.MIDletResources;
import things.implementations.RecordEnumerator;

import java.io.*;
import java.util.Hashtable;

public class RecordStore {
    public static final int AUTHMODE_PRIVATE = 0;
    public static final int AUTHMODE_ANY = 1;

    private static final Hashtable<String, RecordStore> openedRecords = new Hashtable();
    private static String recordsPath = String.format("records/%s/", MIDletResources.getMIDletName());

    private File file;
    private RecordEnumerator recordEnumerator;

    private RecordStore(File file, RecordEnumerator recordEnumerator) {
        this.file = file;
        this.recordEnumerator = recordEnumerator;
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException, IllegalArgumentException {
        if (recordStoreName == null)
            throw new IllegalArgumentException();

        if (openedRecords.containsKey(recordStoreName)) {
            return openedRecords.get(recordStoreName);
        }

        var file = new File(recordsPath + recordStoreName);
        RecordStore recordStore = null;

        try {
            if (file.exists()) {
                var fileInputStream = new FileInputStream(file);
                var objectInputStream = new ObjectInputStream(fileInputStream);

                var recordEnumerator = (RecordEnumerator) objectInputStream.readObject();
                recordStore = new RecordStore(file, recordEnumerator);
            }
            else if (createIfNecessary) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                recordStore = new RecordStore(file, new RecordEnumerator());
                recordStore.writeRecordEnumerator();
            }
            else throw new RecordStoreNotFoundException();
        }
        catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            throw new RecordStoreException();
        }
        catch (RecordStoreNotFoundException exception) {
            exception.printStackTrace();
            throw exception;
        }
        openedRecords.put(recordStoreName, recordStore);
        return recordStore;
    }

    public int getNumRecords() throws RecordStoreNotOpenException {
        // TODO: write method logic
        return 0;
    }

    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) throws RecordStoreNotOpenException {
        return recordEnumerator;
    }

    public int addRecord(byte[] arr, int offset, int numBytes) throws RecordStoreException {
        recordEnumerator.records.add(arr);
        writeRecordEnumerator();
        return recordEnumerator.records.size() - 1;
    }

    public void closeRecordStore() throws RecordStoreException { }

    public void setRecord(int recordId, byte[] arr, int offset, int numBytes) throws RecordStoreException {
        if (recordEnumerator.records.size() <= recordId) {
            throw new RecordStoreException();
        }
        recordEnumerator.records.set(recordId, arr);
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