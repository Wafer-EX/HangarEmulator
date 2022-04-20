package things.utils;

import things.MIDletResources;
import things.implementations.RecordEnumerator;

import java.io.*;

public class RecordStoreUtils {
    public static String getRecordsPath() {
        return String.format("records/%s/", MIDletResources.getMIDletName());
    }

    public static void writeRecordEnumerator(RecordEnumerator recordEnumerator, File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            var fileOutputStream = new FileOutputStream(file, false);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recordEnumerator);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RecordEnumerator readRecordEnumerator(File file) {
        try {
            var fileInputStream = new FileInputStream(file);
            var objectInputStream = new ObjectInputStream(fileInputStream);
            var records = (RecordEnumerator) objectInputStream.readObject();
            return records;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}