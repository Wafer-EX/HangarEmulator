/*
 * Copyright 2022-2024 Wafer EX
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

package aq.waferex.hangaremulator.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.MIDletResources;

import java.io.InputStream;

public class HangarClassVisitor extends ClassVisitor {
    public HangarClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, desc, signature, exceptions)) {
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if (name.equals("getResourceAsStream")) {
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(HangarClassVisitor.class), "getResource", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/io/InputStream;", false);
                }
                else if (name.equals("getProperty")) {
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(HangarClassVisitor.class), "getProperty", "(Ljava/lang/String;)Ljava/lang/String;", false);
                }
                else {
                    mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        };
    }

    @SuppressWarnings("unused")
    public static InputStream getResource(Class<?> inputClass, String resourcePath) {
        return MIDletResources.getResource(resourcePath);
    }

    @SuppressWarnings("unused")
    public static String getProperty(String key) {
        return HangarState.getProperties().getProperty(key);
    }
}