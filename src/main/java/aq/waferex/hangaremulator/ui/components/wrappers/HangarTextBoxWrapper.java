/*
 * Copyright 2024 Wafer EX
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

package aq.waferex.hangaremulator.ui.components.wrappers;

import javax.microedition.lcdui.TextBox;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class HangarTextBoxWrapper extends HangarWrapper {
    private final JTextArea textArea;

    public HangarTextBoxWrapper(TextBox textBox) {
        super(new CardLayout());

        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textBox.setInternalString(textArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textBox.setInternalString(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // I don't know what it's doing
            }
        });

        this.add(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}