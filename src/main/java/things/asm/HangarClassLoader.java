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

package things.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import things.MIDletResources;

import java.net.URL;
import java.net.URLClassLoader;

public class HangarClassLoader extends URLClassLoader {

    public HangarClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!name.contains("java") && !name.contains("nokia") && !name.contains("things")) {
            try {
                var classPath = name.replaceAll("\\.", "/");
                var classStream = MIDletResources.getResource(classPath + ".class");
                assert classStream != null;

                var classReader = new ClassReader(classStream);
                var classWriter = new ClassWriter(0);
                var classVisitor = new HangarClassVisitor(Opcodes.ASM9, classWriter);
                classReader.accept(classVisitor, 0);

                var classByteArray = classWriter.toByteArray();
                return defineClass(name, classByteArray, 0, classByteArray.length);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return super.loadClass(name);
    }
}