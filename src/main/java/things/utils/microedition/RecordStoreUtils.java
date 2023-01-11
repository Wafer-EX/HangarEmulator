/*
 * Copyright 2022-2023 Kirill Lomakin
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

package things.utils.microedition;

import things.MIDletResources;

import javax.microedition.rms.RecordStore;
import java.io.*;

public final class RecordStoreUtils {
    public static String getRecordsPath() {
        return String.format("hangar-data/records/%s/", MIDletResources.getMainClassName());
    }

    public static void writeRecordStore(RecordStore recordStore, File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            var fileOutputStream = new FileOutputStream(file, false);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recordStore);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static RecordStore readRecordStore(File file) {
        try {
            var fileInputStream = new FileInputStream(file);
            var objectInputStream = new ObjectInputStream(fileInputStream);
            return (RecordStore) objectInputStream.readObject();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}