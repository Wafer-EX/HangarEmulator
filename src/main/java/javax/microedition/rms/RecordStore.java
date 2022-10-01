/*
 * Copyright 2022 Kirill Lomakin
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

package javax.microedition.rms;

import things.implementations.rms.RecordEnumerator;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import static things.utils.microedition.RecordStoreUtils.*;

public class RecordStore implements Serializable {
    public static final int AUTHMODE_PRIVATE = 0;
    public static final int AUTHMODE_ANY = 1;

    private static final Hashtable<String, RecordStore> openedRecords = new Hashtable();
    private List<RecordListener> recordListeners = new ArrayList<>();
    private final RecordEnumerator recordEnumerator;
    private final File recordStorePath;
    private final String name;
    private boolean isOpened = true;
    private int authmode = AUTHMODE_PRIVATE;
    private boolean writable = false;
    private int version = 0;

    private RecordStore(String name, File recordStorePath, RecordEnumerator recordEnumerator) {
        this.name = name;
        this.recordStorePath = recordStorePath;
        this.recordEnumerator = recordEnumerator;
    }

    public static void deleteRecordStore(String recordStoreName) throws RecordStoreException, RecordStoreNotFoundException {
        recordStoreName = recordStoreName.replaceAll(" ", "%20");
        if (openedRecords.containsKey(recordStoreName)) {
            openedRecords.remove(recordStoreName);
        }
        else {
            throw new RecordStoreNotFoundException();
        }
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException {
        if (recordStoreName == null) {
            throw new IllegalArgumentException();
        }
        recordStoreName = recordStoreName.replaceAll(" ", "%20");
        if (openedRecords.containsKey(recordStoreName)) {
            return openedRecords.get(recordStoreName);
        }
        else {
            RecordStore recordStore = null;
            var file = new File(getRecordsPath() + recordStoreName);

            if (file.exists()) {
                return readRecordStore(file);
            }
            else if (createIfNecessary) {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    recordStore = new RecordStore(recordStoreName, file, new RecordEnumerator());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    throw new RecordStoreException();
                }
            }
            else {
                throw new RecordStoreNotFoundException();
            }
            openedRecords.put(recordStoreName, recordStore);
            writeRecordStore(recordStore, file);
            return recordStore;
        }
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary, int authmode, boolean writable) throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException {
        var recordStore = openRecordStore(recordStoreName, createIfNecessary);
        recordStore.setMode(authmode, writable);
        return recordStore;
    }

    public static RecordStore openRecordStore(String recordStoreName, String vendorName, String suiteName) throws RecordStoreException, RecordStoreNotFoundException {
        return openRecordStore(recordStoreName, true);
    }

    public void setMode(int authmode, boolean writable) throws RecordStoreException {
        this.authmode = authmode;
        this.writable = writable;
        writeRecordStore(this, recordStorePath);
    }

    public void closeRecordStore() throws RecordStoreNotOpenException, RecordStoreException {
        // TODO: write method logic?
        if (!isOpened) {
            throw new RecordStoreNotOpenException();
        }
    }

    public static String[] listRecordStores() {
        var appFiles = new File(getRecordsPath()).listFiles();
        if (appFiles == null || appFiles.length == 0) {
            return null;
        }
        else {
            var appFileNames = new String[appFiles.length];
            for (int i = 0; i < appFiles.length; i++) {
                appFileNames[i] = appFiles[i].getName();
            }
            return appFileNames;
        }
    }

    public String getName() throws RecordStoreNotOpenException {
        return name;
    }

    public int getVersion() throws RecordStoreNotOpenException {
        return version;
    }

    public int getNumRecords() throws RecordStoreNotOpenException {
        return recordEnumerator.numRecords();
    }

    public int getSize() throws RecordStoreNotOpenException {
        return recordEnumerator.records.size();
    }

    public int getSizeAvailable() throws RecordStoreNotOpenException {
        var homeDir = FileSystemView.getFileSystemView().getHomeDirectory();
        long freeSpace = homeDir.getFreeSpace();
        return (int) freeSpace;
    }

    public long getLastModified() throws RecordStoreNotOpenException {
        // TODO: write method logic
        return 0;
    }

    public void addRecordListener(RecordListener listener) {
        recordListeners.add(listener);
        version++;
        writeRecordStore(this, recordStorePath);
    }

    public void removeRecordListener(RecordListener listener) {
        recordListeners.remove(listener);
        version++;
        writeRecordStore(this, recordStorePath);
    }

    public int getNextRecordID() throws RecordStoreNotOpenException, RecordStoreException {
        return recordEnumerator.nextRecordId();
    }

    public int addRecord(byte[] arr, int offset, int numBytes) throws RecordStoreException {
        if (arr == null) {
            arr = new byte[0];
        }
        byte[] subArray = Arrays.copyOfRange(arr, offset, offset + numBytes);
        recordEnumerator.records.add(subArray);
        for (var recordListener : recordListeners) {
            recordListener.recordAdded(this, getNumRecords());
        }
        version++;
        writeRecordStore(this, recordStorePath);
        return recordEnumerator.numRecords();
    }

    public void deleteRecord(int recordId) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
        // TODO: write method logic
        for (var recordListener : recordListeners) {
            recordListener.recordDeleted(this, recordId);
        }
        version++;
        writeRecordStore(this, recordStorePath);
    }

    public int getRecordSize(int recordId) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
        var record = getRecord(recordId);
        return record.length;
    }

    public int getRecord(int recordId, byte[] buffer, int offset) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException, ArrayIndexOutOfBoundsException {
        var record = getRecord(recordId);
        if (record.length > buffer.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int bytesCopied = 0;
        for (int i = offset, j = 0; i < buffer.length + offset && j < record.length; i++, j++) {
            buffer[i] = record[j];
            bytesCopied++;
        }
        return bytesCopied;
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
        for (var recordListener : recordListeners) {
            recordListener.recordChanged(this, getNumRecords());
        }
        version++;
        writeRecordStore(this, recordStorePath);
    }

    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) throws RecordStoreNotOpenException {
        // TODO: write logic for filter and comparator
        return recordEnumerator;
    }
}