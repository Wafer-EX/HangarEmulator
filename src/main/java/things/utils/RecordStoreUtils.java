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

package things.utils;

import things.MIDletResources;
import things.implementations.RecordEnumerator;

import java.io.*;

public final class RecordStoreUtils {
    public static String getRecordsPath() {
        return String.format("hangar-data/records/%s/", MIDletResources.getMainClassName());
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
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static RecordEnumerator readRecordEnumerator(File file) {
        try {
            var fileInputStream = new FileInputStream(file);
            var objectInputStream = new ObjectInputStream(fileInputStream);
            var records = (RecordEnumerator) objectInputStream.readObject();
            return records;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}