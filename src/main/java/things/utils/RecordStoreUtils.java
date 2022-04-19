package things.utils;

import things.MIDletResources;

import java.io.*;
import java.util.List;

public class RecordStoreUtils {
    public static String getRecordsPath() {
        return String.format("records/%s/", MIDletResources.getMIDletName());
    }

    public static void writeRecordEnumeratorData(List<byte[]> records, File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            var fileOutputStream = new FileOutputStream(file, false);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(records);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<byte[]> readRecordEnumeratorData(File file) {
        try {
            var fileInputStream = new FileInputStream(file);
            var objectInputStream = new ObjectInputStream(fileInputStream);
            var records = (List<byte[]>) objectInputStream.readObject();
            return records;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}